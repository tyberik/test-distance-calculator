package ru.test.distance.calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistanceRepository distanceRepository;

    public CityDto getCityById(Long id) {
        CityEntity cityEntity = cityRepository.findById(id).orElse(null);
        if (cityEntity == null) {
            return null;
        }
        return CityMapper.toDto(cityEntity);
    }

    public CityExtendDto getCity(String name) {
        CityEntity cityEntity = cityRepository.findCityByCity(name);
        return CityMapper.toExtendDto(cityEntity);
    }

    public List<CityShortDto> getCities() {
        List<CityEntity> cityEntity = cityRepository.findAll();
        return cityEntity.stream().map(CityMapper::toDtoCities).collect(Collectors.toList());
    }

    @Transactional
    public CityDto saveCity(CityDto cityDto) {
        CityEntity cityEntity = CityMapper.toEntity(cityDto);
        CityEntity save = cityRepository.save(cityEntity);
        return CityMapper.toDto(save);
    }

    @Transactional
    public CityDto updateCity() {
        CityEntity cityEntity = cityRepository.findById(1L).get();
        cityEntity.setName("SAMARA2");
        CityEntity save = cityRepository.save(cityEntity);
        return CityMapper.toDto(save);
    }

    @Transactional
    public void saveCities(CitiesRequestDto citiesRequestDto) {
        List<CityEntity> cityEntity = citiesRequestDto.getCities().stream().map(CityMapper::toEntity).collect(Collectors.toList());
        List<CityEntity> save = cityRepository.saveAll(cityEntity);
        Map<String, Long> map =
                save.stream().collect(Collectors.toMap(CityEntity::getName, CityEntity::getId));
        List<DistanceEntity> distanceEntities = DistanceMapper.toEntity(map, citiesRequestDto.getDistances());
        distanceRepository.saveAll(distanceEntities);
    }
}
