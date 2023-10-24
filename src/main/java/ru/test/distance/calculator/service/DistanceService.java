package ru.test.distance.calculator.service;

import ru.test.distance.calculator.dto.CitiesRequestDto;
import ru.test.distance.calculator.dto.DistanceToMatrixDto;

import java.util.List;
import java.util.Map;

public interface DistanceService {

    List<DistanceToMatrixDto> findDistancesToMatrix();

    void saveDistances(Map<String, Long> cityMapNameToId, CitiesRequestDto citiesRequestDto);
}
