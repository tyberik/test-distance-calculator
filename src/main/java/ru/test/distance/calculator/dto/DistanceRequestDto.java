package ru.test.distance.calculator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistanceRequestDto {
    private String fromCity;
    private String toCity;
    private Long distance;
}
