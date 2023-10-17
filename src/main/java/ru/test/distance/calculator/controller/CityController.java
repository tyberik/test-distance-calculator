package ru.test.distance.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.test.distance.calculator.dto.CitiesRequestDto;
import ru.test.distance.calculator.dto.CityShortDto;
import ru.test.distance.calculator.dto.CityDto;
import ru.test.distance.calculator.dto.CityExtendDto;
import ru.test.distance.calculator.service.CityService;

import java.util.List;

@RestController
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping("/greeting/{id}")
    public CityDto greeting(@PathVariable() Long id) {
        return cityService.getCityById(id);
    }

    @GetMapping("/getCity/{name}")
    public CityExtendDto getCity(@PathVariable() String name) {
        return cityService.getCity(name);
    }

    @GetMapping("/cities")
    public List<CityShortDto> getCities() {
        return cityService.getCities();
    }

    @PostMapping("/city")
    public CityDto saveCity(@RequestBody CityDto cityDto) {
        return cityService.saveCity(cityDto);
    }

    @PostMapping("/cities")
    public void saveCity(@RequestBody CitiesRequestDto citiesRequestDto) {
        cityService.saveCities(citiesRequestDto);
    }

    @PostMapping("/cities1")
    public void getCities1(@RequestBody MultipartFile file) {
        cityService.saveCities1(file);
    }
}
