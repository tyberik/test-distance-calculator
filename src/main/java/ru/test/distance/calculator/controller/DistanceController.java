package ru.test.distance.calculator.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.test.distance.calculator.dto.CalculateDistancedDto;
import ru.test.distance.calculator.dto.CalculateResponseDto;
import ru.test.distance.calculator.service.CalculateService;

import java.util.List;

@RestController
@RequestMapping("/distance")
public class DistanceController {

    @Autowired
    private CalculateService calculateService;

    @PostMapping("/calculate")
    public List<CalculateResponseDto> calculateDistance(@RequestBody @Valid CalculateDistancedDto calculateDistancedDto) {
        return calculateService.calculateDistance(calculateDistancedDto);
    }
}
