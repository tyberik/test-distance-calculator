package ru.test.distance.calculator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull
    @NotEmpty(message = "name should not be empty")
    @Size(min = 1, max = 100, message = "name should be between 1 and 100")
    private String name;

    @NotNull
    @Min(value = 0, message = "latitude should be greater than 0")
    private Double latitude;

    @NotNull
    @Min(value = 0, message = "longitude should be greater than 0")
    private Double longitude;

    @OneToMany(mappedBy = "toCityEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DistanceEntity> distanceToCity = new ArrayList<>();

    @OneToMany(mappedBy = "fromCityEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DistanceEntity> distanceFromCity = new ArrayList<>();

}
