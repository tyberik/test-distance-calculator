package ru.test.distance.calculator.controller;

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

    @PostMapping("/file")
    public void saveAllFromFile(@RequestBody MultipartFile file) {
        cityService.saveCities(file);
    }
}
