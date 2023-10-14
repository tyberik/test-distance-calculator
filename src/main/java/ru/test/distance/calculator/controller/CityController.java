package ru.test.distance.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.test.distance.calculator.dto.CitiesDto;
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

    @GetMapping("getCity/{name}")
    public CityExtendDto getCity(@PathVariable() String name) {
        return cityService.getCity(name);
    }

    @GetMapping("/cities")
    public List<CitiesDto> getCities() {
        return cityService.getCities();
    }
}
