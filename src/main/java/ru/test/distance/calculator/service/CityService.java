package ru.test.distance.calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.distance.calculator.dto.CityDto;
import ru.test.distance.calculator.dto.CityExtendDto;
import ru.test.distance.calculator.entity.CityEntity;
import ru.test.distance.calculator.mapper.CityMapper;
import ru.test.distance.calculator.repository.CityRepository;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

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
}
