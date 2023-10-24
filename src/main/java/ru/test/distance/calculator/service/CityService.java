package ru.test.distance.calculator.service;

import org.springframework.web.multipart.MultipartFile;
import ru.test.distance.calculator.dto.CityDto;
import ru.test.distance.calculator.dto.CityShortDto;

import java.util.List;

public interface CityService {

    List<CityShortDto> getCities();

    void saveCities(MultipartFile file);

    CityDto findFromCityByIdWithDistance(Long id);

    CityDto findToCityByIdWithDistance(Long id);

    List<CityShortDto> findAllCities();
}
