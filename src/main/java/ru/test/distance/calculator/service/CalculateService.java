package ru.test.distance.calculator.service;

import ru.test.distance.calculator.dto.CalculateDistancedDto;
import ru.test.distance.calculator.dto.CalculateResponseDto;

import java.util.List;

public interface CalculateService {
    List<CalculateResponseDto> calculateDistance(CalculateDistancedDto calculateDistancedDto);
}
