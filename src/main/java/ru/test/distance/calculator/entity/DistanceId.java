package ru.test.distance.calculator.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
public class DistanceId implements Serializable {
    private Long fromCity;
    private Long toCity;
}
