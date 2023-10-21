package ru.test.distance.calculator.mapper;

import ru.test.distance.calculator.dto.DistanceRequestDto;
import ru.test.distance.calculator.dto.DistanceToMatrixDto;
import ru.test.distance.calculator.entity.DistanceEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DistanceMapper {

    public static List<DistanceEntity> toEntity(Map<String, Long> map, List<DistanceRequestDto> distanceRequestDto) {
        return distanceRequestDto.stream()
                .map(s -> {
                    DistanceEntity distanceEntity = new DistanceEntity();
                    distanceEntity.setFromCity(map.get(s.getFromCity()));
                    distanceEntity.setToCity(map.get(s.getToCity()));
                    distanceEntity.setDistance(s.getDistance());
                    return distanceEntity;
                }).collect(Collectors.toList());
    }

    public static DistanceToMatrixDto toMatrixDtos(DistanceEntity distanceEntity) {
        DistanceToMatrixDto distance = new DistanceToMatrixDto();
        distance.setFromCity(distanceEntity.getFromCity());
        distance.setToCity(distanceEntity.getToCity());
        distance.setDistance(distanceEntity.getDistance());
        return distance;
    }
}
