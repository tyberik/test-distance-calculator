package ru.test.distance.calculator.mapper;

import ru.test.distance.calculator.dto.CitiesRequestDto;
import ru.test.distance.calculator.dto.DistanceRequestDto;
import ru.test.distance.calculator.entity.CityEntity;
import ru.test.distance.calculator.entity.DistanceEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DistanceMapper {
    public static List<DistanceEntity> toEntity(Map<String, Long> map, List<DistanceRequestDto> distanceRequestDto) {

        List<DistanceEntity> collect = distanceRequestDto.stream()
                .map(s -> {
                    DistanceEntity distanceEntity = new DistanceEntity();
                    distanceEntity.setFromCity(map.get(s.getFromCity()));
                    distanceEntity.setToCity(map.get(s.getToCity()));
                    distanceEntity.setDistance(s.getDistance());
                    return distanceEntity;
                }).collect(Collectors.toList());
        return collect;
    }
}
