package ru.test.distance.calculator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CitiesRequestDto {
    private List<CityDto> cities;
    private List<DistanceRequestDto> distances;
}
