package ru.test.distance.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.distance.calculator.entity.CityEntity;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
}
