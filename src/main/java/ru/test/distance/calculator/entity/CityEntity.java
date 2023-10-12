package ru.test.distance.calculator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "city")
@Getter
@Setter
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double latitude;

    private Double longitude;

    @OneToMany(mappedBy = "toCityEntity")
    private List<DistanceEntity> distanceToCity = new ArrayList<>();

    @OneToMany(mappedBy = "fromCityEntity")
    private List<DistanceEntity> distanceFromCity = new ArrayList<>();
}
