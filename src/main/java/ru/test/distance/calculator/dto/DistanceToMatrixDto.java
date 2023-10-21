package ru.test.distance.calculator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistanceToMatrixDto {
    private Long fromCity;
    private Long toCity;
    private Long distance;
}
