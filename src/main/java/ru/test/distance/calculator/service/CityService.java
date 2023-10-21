package ru.test.distance.calculator.service;

import org.springframework.web.multipart.MultipartFile;
import ru.test.distance.calculator.dto.CalculateDistancedDto;
import ru.test.distance.calculator.dto.CalculateResponseDto;
import ru.test.distance.calculator.dto.CityShortDto;

import java.util.List;

public interface CityService {

    List<CityShortDto> getCities();

    void saveCities(MultipartFile file);

    List<CalculateResponseDto> getDistance(CalculateDistancedDto calculateDistancedDto);
}
