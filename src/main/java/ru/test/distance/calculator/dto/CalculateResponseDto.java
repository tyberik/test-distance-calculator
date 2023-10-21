package ru.test.distance.calculator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculateResponseDto {
    private String fromCity;
    private String toCity;
    private CalculationTypeEnum type;
    private Long distance;
}
