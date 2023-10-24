package ru.test.distance.calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.test.distance.calculator.dto.*;
import ru.test.distance.calculator.entity.CityEntity;
import ru.test.distance.calculator.mapper.CityMapper;
import ru.test.distance.calculator.repository.CityRepository;
import ru.test.distance.calculator.util.ParsingFileExecutor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistanceService distanceService;

    @Autowired
    private ParsingFileExecutor parsingFileExecutor;


    @Override
    public List<CityShortDto> getCities() {
        List<CityEntity> cityEntity = cityRepository.findAll();
        return cityEntity.stream().map(CityMapper::toDtoCityShort).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void saveCities(MultipartFile file) {
        CitiesRequestDto citiesRequestDto = parsingFileExecutor.parseCitiesRequestDto(file);
        List<CityEntity> cityEntity = citiesRequestDto.getCities().stream().map(CityMapper::toEntity).collect(Collectors.toList());
        List<CityEntity> save = cityRepository.saveAll(cityEntity);
        Map<String, Long> cityMapNameToId =
                save.stream().collect(Collectors.toMap(CityEntity::getName, CityEntity::getId));
        distanceService.saveDistances(cityMapNameToId, citiesRequestDto);
    }

    @Override
    public CityDto findFromCityByIdWithDistance(Long id) {
        CityEntity cityEntity = cityRepository.findFromCityByIdWithDistance(id);
        return CityMapper.toDtoCity(cityEntity);
    }

    @Override
    public CityDto findToCityByIdWithDistance(Long id) {
        CityEntity cityEntity = cityRepository.findToCityByIdWithDistance(id);
        return CityMapper.toDtoCity(cityEntity);
    }

    @Override
    public List<CityShortDto> findAllCities() {
        List<CityEntity> cityEntities = cityRepository.findAll();
        return cityEntities.stream().map(CityMapper::toDtoCityShort).collect(Collectors.toList());
    }

}

