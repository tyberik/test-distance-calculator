package ru.test.distance.calculator.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.test.distance.calculator.dto.*;
import ru.test.distance.calculator.entity.CityEntity;
import ru.test.distance.calculator.entity.DistanceEntity;
import ru.test.distance.calculator.exception.InvalidFileException;
import ru.test.distance.calculator.exception.InvalidSizeListsException;
import ru.test.distance.calculator.mapper.CityMapper;
import ru.test.distance.calculator.mapper.DistanceMapper;
import ru.test.distance.calculator.repository.CityRepository;
import ru.test.distance.calculator.repository.DistanceRepository;
import ru.test.distance.calculator.util.DistanceCalculator;
import ru.test.distance.calculator.util.DistanceMatrixCalculator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.test.distance.calculator.dto.CalculationTypeEnum.*;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistanceRepository distanceRepository;

    @Autowired
    private DistanceMatrixCalculator distanceMatrixCalculator;


    @Override
    public List<CityShortDto> getCities() {
        List<CityEntity> cityEntity = cityRepository.findAll();
        return cityEntity.stream().map(CityMapper::toDtoCities).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void saveCities(MultipartFile file) {
        CitiesRequestDto citiesRequestDto = null;
        try {
            InputStream is = file.getInputStream();
            XmlMapper xmlMapper = new XmlMapper();
            JavaType transactionType = xmlMapper.getTypeFactory().constructType(CitiesRequestDto.class);
            citiesRequestDto = xmlMapper.readValue(is, transactionType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidFileException("file is null");
        }
        List<CityEntity> cityEntity = citiesRequestDto.getCities().stream().map(CityMapper::toEntity).collect(Collectors.toList());
        List<CityEntity> save = cityRepository.saveAll(cityEntity);
        Map<String, Long> map =
                save.stream().collect(Collectors.toMap(CityEntity::getName, CityEntity::getId));
        List<DistanceEntity> distanceEntities = DistanceMapper.toEntity(map, citiesRequestDto.getDistances());
        distanceRepository.saveAll(distanceEntities);
    }

    @Override
    public List<CalculateResponseDto> getDistance(CalculateDistancedDto calculateDistancedDto) {
        if (calculateDistancedDto.getFromCities().size() != calculateDistancedDto.getToCities().size()) {
            throw new InvalidSizeListsException("Invalid size Lists");
        }
        CalculationTypeEnum calculationTypeEnumEnum = calculateDistancedDto.getCalculationType();
        List<CalculateResponseDto> calculateResponseDtos = new ArrayList<>();
        switch (calculationTypeEnumEnum) {
            case CROWFLIGHT -> calculateResponseDtos.addAll(getCity(calculateDistancedDto.getFromCities(), calculateDistancedDto.getToCities()));
            case DISTANCE_MATRIX -> calculateResponseDtos.addAll(getDistanceMatrix(calculateDistancedDto.getFromCities(), calculateDistancedDto.getToCities()));
            case ALL -> {
                calculateResponseDtos.addAll(getCity(calculateDistancedDto.getFromCities(), calculateDistancedDto.getToCities()));
                calculateResponseDtos.addAll(getDistanceMatrix(calculateDistancedDto.getFromCities(), calculateDistancedDto.getToCities()));
            }
        }
        return calculateResponseDtos;
    }

    private List<CalculateResponseDto> getCity(List<CityShortDto> fromCity, List<CityShortDto> toCity) {
        List<CalculateResponseDto> calculateResponseDtos = new ArrayList<>();

        for (int i = 0; i < fromCity.size(); i++) {
            CityEntity cityEntityFrom = cityRepository.findCityByIdWithDistance(fromCity.get(i).getId());
            CityEntity cityEntityTo = cityRepository.findCityByIdWithDistance(toCity.get(i).getId());

            Point pointFrom = new Point(cityEntityFrom.getLatitude(), cityEntityFrom.getLongitude());
            Point pointTo = new Point(cityEntityTo.getLatitude(), cityEntityTo.getLongitude());
            double distance = DistanceCalculator.getDistance(pointFrom, pointTo);

            CalculateResponseDto calculateResponseDto = new CalculateResponseDto();
            calculateResponseDto.setType(CROWFLIGHT);
            calculateResponseDto.setDistance((long) distance);
            calculateResponseDto.setFromCity(cityEntityFrom.getName());
            calculateResponseDto.setToCity(cityEntityTo.getName());
            calculateResponseDtos.add(calculateResponseDto);
        }
        return calculateResponseDtos;
    }

    private List<CalculateResponseDto> getDistanceMatrix(List<CityShortDto> fromCity, List<CityShortDto> toCity) {
        List<CityEntity> cityEntities = cityRepository.findAll();
        List<DistanceEntity> distanceEntities = distanceRepository.findAll();

        List<CityShortDto> collectFrom = cityEntities.stream().map(CityMapper::toDtoCities).collect(Collectors.toList());
        List<DistanceToMatrixDto> collectTo = distanceEntities.stream().map(DistanceMapper::toMatrixDtos).collect(Collectors.toList());

        return distanceMatrixCalculator.calc(collectFrom, collectTo, fromCity, toCity);
    }

}

