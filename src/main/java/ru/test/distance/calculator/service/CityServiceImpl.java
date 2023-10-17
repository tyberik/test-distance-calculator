package ru.test.distance.calculator.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.test.distance.calculator.dto.CitiesRequestDto;
import ru.test.distance.calculator.dto.CityShortDto;
import ru.test.distance.calculator.dto.CityDto;
import ru.test.distance.calculator.dto.CityExtendDto;
import ru.test.distance.calculator.entity.CityEntity;
import ru.test.distance.calculator.entity.DistanceEntity;
import ru.test.distance.calculator.mapper.CityMapper;
import ru.test.distance.calculator.mapper.DistanceMapper;
import ru.test.distance.calculator.repository.CityRepository;
import ru.test.distance.calculator.repository.DistanceRepository;
import ru.test.distance.calculator.util.DistanceMatrixCalculator;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistanceRepository distanceRepository;

    @Autowired
    private DistanceMatrixCalculator distanceMatrixCalculator;

    @Override
    public CityDto getCityById(Long id) {
        CityEntity cityEntity = cityRepository.findById(id).orElse(null);
        if (cityEntity == null) {
            return null;
        }
        return CityMapper.toDto(cityEntity);
    }

    @Override
    public CityExtendDto getCity(String name) {
        CityEntity cityEntity = cityRepository.findCityByCity(name);
        return CityMapper.toExtendDto(cityEntity);
    }

    @Override
    public List<CityShortDto> getCities() {
        List<CityEntity> cityEntity = cityRepository.findAll();
        return cityEntity.stream().map(CityMapper::toDtoCities).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CityDto saveCity(CityDto cityDto) {
        CityEntity cityEntity = CityMapper.toEntity(cityDto);
        CityEntity save = cityRepository.save(cityEntity);
        return CityMapper.toDto(save);
    }

    @Transactional
    @Override
    public CityDto updateCity() {
        CityEntity cityEntity = cityRepository.findById(1L).get();
        cityEntity.setName("SAMARA2");
        CityEntity save = cityRepository.save(cityEntity);
        return CityMapper.toDto(save);
    }

    @Transactional
    @Override
    public void saveCities(CitiesRequestDto citiesRequestDto) {
        List<CityEntity> cityEntity = citiesRequestDto.getCities().stream().map(CityMapper::toEntity).collect(Collectors.toList());
        List<CityEntity> save = cityRepository.saveAll(cityEntity);
        Map<String, Long> map =
                save.stream().collect(Collectors.toMap(CityEntity::getName, CityEntity::getId));
        List<DistanceEntity> distanceEntities = DistanceMapper.toEntity(map, citiesRequestDto.getDistances());
        distanceRepository.saveAll(distanceEntities);
    }

    @Transactional
    @Override
    public void saveCities1(MultipartFile file) {
        CitiesRequestDto citiesRequestDto = null;
        try {
            InputStream is = file.getInputStream();
            XmlMapper xmlMapper = new XmlMapper();
            JavaType transactionType = xmlMapper.getTypeFactory().constructType(CitiesRequestDto.class);
            citiesRequestDto = xmlMapper.readValue(is, transactionType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO
        if (citiesRequestDto == null) {
            throw new RuntimeException("file is null");
        }
        List<CityEntity> cityEntity = citiesRequestDto.getCities().stream().map(CityMapper::toEntity).collect(Collectors.toList());
        List<CityEntity> save = cityRepository.saveAll(cityEntity);
        Map<String, Long> map =
                save.stream().collect(Collectors.toMap(CityEntity::getName, CityEntity::getId));
        List<DistanceEntity> distanceEntities = DistanceMapper.toEntity(map, citiesRequestDto.getDistances());
        distanceRepository.saveAll(distanceEntities);
    }

    @Override
    public void getDistanceMatrix(Long fromCity, Long toCity) {
        List<CityEntity> cityEntities = cityRepository.findAll();
        List<DistanceEntity> distanceEntities = distanceRepository.findAll();


        distanceMatrixCalculator.calc(cityEntities, distanceEntities,fromCity, toCity);

    }
}

