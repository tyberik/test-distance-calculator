package ru.test.distance.calculator.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.test.distance.calculator.dto.CitiesRequestDto;
import ru.test.distance.calculator.dto.CityDto;
import ru.test.distance.calculator.dto.CityExtendDto;
import ru.test.distance.calculator.dto.CityShortDto;

import java.util.List;

public interface CityService {
    CityDto getCityById(Long id);

    CityExtendDto getCity(String name);

    List<CityShortDto> getCities();

    CityDto saveCity(CityDto cityDto);

    CityDto updateCity();

    void saveCities(CitiesRequestDto citiesRequestDto);

    void saveCities1(MultipartFile file);

    void getDistanceMatrix(Long fromCity, Long toCity);
}
