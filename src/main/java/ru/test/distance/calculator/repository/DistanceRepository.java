package ru.test.distance.calculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.distance.calculator.entity.DistanceEntity;
import ru.test.distance.calculator.entity.DistanceId;

public interface DistanceRepository extends JpaRepository<DistanceEntity, DistanceId> {
}
