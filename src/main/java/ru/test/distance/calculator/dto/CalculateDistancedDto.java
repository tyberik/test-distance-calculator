package ru.test.distance.calculator.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CalculateDistancedDto {
    @NotNull
    private CalculationTypeEnum calculationType;
    @NotNull
    @NotEmpty(message = "Input city list cannot be empty.")
    private List<@Valid CityShortDto> fromCities;
    @NotNull
    @NotEmpty(message = "Input city list cannot be empty.")
    private List<@Valid CityShortDto> toCities;
}
