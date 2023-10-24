package ru.test.distance.calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.distance.calculator.dto.CitiesRequestDto;
import ru.test.distance.calculator.dto.DistanceToMatrixDto;
import ru.test.distance.calculator.entity.DistanceEntity;
import ru.test.distance.calculator.mapper.DistanceMapper;
import ru.test.distance.calculator.repository.DistanceRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DistanceServiceImpl implements DistanceService{

    @Autowired
    private DistanceRepository distanceRepository;


    @Override
    public List<DistanceToMatrixDto> findDistancesToMatrix() {
        List<DistanceEntity> distanceEntities = distanceRepository.findAll();

        return distanceEntities.stream().map(DistanceMapper::toMatrixDtos).collect(Collectors.toList());
    }

    @Override
    public void saveDistances(Map<String, Long> cityMapNameToId, CitiesRequestDto citiesRequestDto) {
        List<DistanceEntity> distanceEntities = DistanceMapper.toEntity(cityMapNameToId, citiesRequestDto.getDistances());
        distanceRepository.saveAll(distanceEntities);
    }
}
