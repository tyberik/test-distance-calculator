package ru.test.distance.calculator.mapper;

import ru.test.distance.calculator.dto.*;
import ru.test.distance.calculator.entity.CityEntity;

public class CityMapper {

    public static CityEntity toEntity(CityDto cityDto) {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName(cityDto.getName());
        cityEntity.setLatitude(cityDto.getLatitude());
        cityEntity.setLongitude(cityDto.getLongitude());
        return cityEntity;
    }

    public static CityShortDto toDtoCityShort(CityEntity cityEntity) {
        CityShortDto cityShortDto = new CityShortDto();
        cityShortDto.setId(cityEntity.getId());
        cityShortDto.setName(cityEntity.getName());
        return cityShortDto;
    }

    public static CityDto toDtoCity(CityEntity cityEntity){
        CityDto cityDto = new CityDto();
        cityDto.setName(cityEntity.getName());
        cityDto.setLatitude(cityEntity.getLatitude());
        cityDto.setLongitude(cityEntity.getLongitude());
        return cityDto;
    }
}
