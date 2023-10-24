package ru.test.distance.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.test.distance.calculator.entity.CityEntity;

public interface CityRepository extends JpaRepository<CityEntity, Long> {

    @Query("SELECT cityEntity from CityEntity cityEntity join cityEntity.distanceFromCity where cityEntity.id = :id")
    CityEntity findFromCityByIdWithDistance(Long id);

    @Query("SELECT cityEntity from CityEntity cityEntity join cityEntity.distanceToCity where cityEntity.id = :id")
    CityEntity findToCityByIdWithDistance(Long id);
}
