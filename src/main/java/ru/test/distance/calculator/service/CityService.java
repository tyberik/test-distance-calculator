package ru.test.distance.calculator.service;

import org.springframework.web.multipart.MultipartFile;
import ru.test.distance.calculator.dto.CitiesRequestDto;
import ru.test.distance.calculator.dto.CityDto;
import ru.test.distance.calculator.dto.CityExtendDto;
import ru.test.distance.calculator.dto.CityShortDto;

import java.util.List;

public interface CityService {
    public CityDto getCityById(Long id);
    public CityExtendDto getCity(String name);
    public List<CityShortDto> getCities();
    public CityDto saveCity(CityDto cityDto);
    public CityDto updateCity();
    public void saveCities(CitiesRequestDto citiesRequestDto);
    public void saveCities1(MultipartFile file);
}
