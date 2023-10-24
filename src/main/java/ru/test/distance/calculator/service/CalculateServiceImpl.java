package ru.test.distance.calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.distance.calculator.dto.*;
import ru.test.distance.calculator.util.DistanceCalculator;
import ru.test.distance.calculator.util.DistanceMatrixCalculator;

import java.util.ArrayList;
import java.util.List;

import static ru.test.distance.calculator.dto.CalculationTypeEnum.CROWFLIGHT;

@Service
public class CalculateServiceImpl implements CalculateService{

    @Autowired
    private DistanceMatrixCalculator distanceMatrixCalculator;

    @Autowired
    private DistanceService distanceService;

    @Autowired
    private CityService cityService;

    @Override
    public List<CalculateResponseDto> calculateDistance(CalculateDistancedDto calculateDistancedDto) {
        CalculationTypeEnum calculationTypeEnumEnum = calculateDistancedDto.getCalculationType();
        List<CalculateResponseDto> calculateResponseDtos = new ArrayList<>();
        switch (calculationTypeEnumEnum) {
            case CROWFLIGHT -> calculateResponseDtos.addAll(getCrowflightDistance(calculateDistancedDto.getFromCities(), calculateDistancedDto.getToCities()));
            case DISTANCE_MATRIX -> calculateResponseDtos.addAll(getDistanceMatrix(calculateDistancedDto.getFromCities(), calculateDistancedDto.getToCities()));
            case ALL -> {
                calculateResponseDtos.addAll(getCrowflightDistance(calculateDistancedDto.getFromCities(), calculateDistancedDto.getToCities()));
                calculateResponseDtos.addAll(getDistanceMatrix(calculateDistancedDto.getFromCities(), calculateDistancedDto.getToCities()));
            }
        }
        return calculateResponseDtos;
    }

    private List<CalculateResponseDto> getCrowflightDistance(List<CityShortDto> fromCities, List<CityShortDto> toCities) {
        List<CalculateResponseDto> calculateResponseDtos = new ArrayList<>();

        for (CityShortDto cityFromShortDto : fromCities) {
            CityDto cityFrom = cityService.findFromCityByIdWithDistance(cityFromShortDto.getId());
            for (CityShortDto cityToShortDto : toCities) {
                CityDto cityTo = cityService.findToCityByIdWithDistance(cityToShortDto.getId());

                Point pointFrom = new Point(cityFrom.getLatitude(), cityFrom.getLongitude());
                Point pointTo = new Point(cityTo.getLatitude(), cityTo.getLongitude());
                double distance = DistanceCalculator.getDistance(pointFrom, pointTo);

                CalculateResponseDto calculateResponseDto = new CalculateResponseDto();
                calculateResponseDto.setType(CROWFLIGHT);
                calculateResponseDto.setDistance((long) distance);
                calculateResponseDto.setFromCity(cityFrom.getName());
                calculateResponseDto.setToCity(cityTo.getName());
                calculateResponseDtos.add(calculateResponseDto);
            }
        }
        return calculateResponseDtos;
    }

    private List<CalculateResponseDto> getDistanceMatrix(List<CityShortDto> fromCity, List<CityShortDto> toCity) {
        List<CityShortDto> allCities = cityService.findAllCities();
        List<DistanceToMatrixDto> distancesTo = distanceService.findDistancesToMatrix();

        return distanceMatrixCalculator.calc(allCities, distancesTo, fromCity, toCity);
    }
}
