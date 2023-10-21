package ru.test.distance.calculator.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityShortDto {
    @NotNull
    @Min(value = 0, message = "id should be greater than 0")
    private Long Id;
    @NotBlank
    private String name;
}
