package ru.test.distance.calculator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "distance")
@Getter
@Setter
@IdClass(DistanceId.class)
public class DistanceEntity {

    @Id
    @Column(name = "from_city")
    private Long fromCity;

    @Id
    @Column(name = "to_city")
    private Long toCity;

    @NotNull
    @Min(value = 0, message = "distance should be greater than 0")
    private Long distance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_city", updatable = false, insertable = false)
    private CityEntity toCityEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_city", updatable = false, insertable = false)
    private CityEntity fromCityEntity;
}
