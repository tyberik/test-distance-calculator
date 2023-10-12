package ru.test.distance.calculator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CityExtendDto {

    private String name;
    private Double latitude;
    private Double longitude;
    private List<DistanceDto> distanceDtoList;


}
