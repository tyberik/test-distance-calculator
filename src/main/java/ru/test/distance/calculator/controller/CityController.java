package ru.test.distance.calculator.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.test.distance.calculator.dto.*;
import ru.test.distance.calculator.service.CityService;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping
    public List<CityShortDto> getCities() {
        return cityService.getCities();
    }

    @PostMapping("/calculateDistance")
    public List<CalculateResponseDto> calculateDistance(@RequestBody @Valid CalculateDistancedDto calculateDistancedDto) {
        return cityService.getDistance(calculateDistancedDto);
    }

    @PostMapping("/file")
    public void getCities1(@RequestBody MultipartFile file) {
        cityService.saveCities(file);
    }
}
