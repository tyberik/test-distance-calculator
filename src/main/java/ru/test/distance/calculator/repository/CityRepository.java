package ru.test.distance.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.test.distance.calculator.entity.CityEntity;

import java.util.List;

public interface CityRepository extends JpaRepository<CityEntity, Long> {

    @Query("SELECT cityEntity from CityEntity cityEntity join cityEntity.distanceFromCity where cityEntity.name = :name")
    CityEntity findCityByCity(String name);
}
