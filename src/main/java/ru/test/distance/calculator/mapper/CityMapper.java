package ru.test.distance.calculator.mapper;

import ru.test.distance.calculator.dto.*;
import ru.test.distance.calculator.entity.CityEntity;
import ru.test.distance.calculator.util.DistanceCalculator;

import java.util.List;
import java.util.stream.Collectors;

public class CityMapper {
    public static CityDto toDto(CityEntity cityEntity) {
        CityDto cityDto = new CityDto();
        cityDto.setName(cityEntity.getName());
        cityDto.setLatitude(cityEntity.getLatitude());
        cityDto.setLongitude(cityEntity.getLongitude());
        return cityDto;
    }

    public static CityEntity toEntity(CityDto cityDto) {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName(cityDto.getName());
        cityEntity.setLatitude(cityDto.getLatitude());
        cityEntity.setLongitude(cityDto.getLongitude());
        return cityEntity;
    }

    // можно сократить
    public static CityExtendDto toExtendDto(CityEntity cityEntity) {
        CityExtendDto cityExtendDto = new CityExtendDto();
        cityExtendDto.setName(cityEntity.getName());
        cityExtendDto.setLatitude(cityEntity.getLatitude());
        cityExtendDto.setLongitude(cityEntity.getLongitude());
        List<DistanceDto> collect = cityEntity.getDistanceFromCity().stream()
                .map(s -> {
                    Point pointFrom = new Point(cityEntity.getLatitude(), cityEntity.getLongitude());
                    Point pointTo = new Point(s.getToCityEntity().getLatitude(), s.getToCityEntity().getLongitude());
                            DistanceDto distanceDto = new DistanceDto();
                            distanceDto.setName(s.getToCityEntity().getName());
                            distanceDto.setDistance((long) DistanceCalculator.getDistance(pointFrom, pointTo));
                            return distanceDto;
                        }
                )
                .collect(Collectors.toList());
        cityExtendDto.setDistanceDtoList(collect);
        return cityExtendDto;
    }

    public static CityShortDto toDtoCities(CityEntity cityEntity){
        CityShortDto cityShortDto = new CityShortDto();
        cityShortDto.setId(cityEntity.getId());
        cityShortDto.setName(cityEntity.getName());
        return cityShortDto;
    }


}
