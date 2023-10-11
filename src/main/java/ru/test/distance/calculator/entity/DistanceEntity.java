package ru.test.distance.calculator.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "distance")
@Getter
@Setter
public class DistanceEntity {

    @EmbeddedId
    private DistanceId id;

    private Long distance;
}

@Embeddable
@Setter
@Getter
class DistanceId implements Serializable {
    private Long from_city;
    private Long to_city;
}
