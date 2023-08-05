package com.hatip.inhousenavigation.model.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "BASE_STATION_ENTITY")
public class BaseStationEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(name = "ID")
    private UUID id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "X")
    private Float x;
    @Column(name = "Y")
    private Float y;
    @Column(name = "DETECTION_RADIUS_IN_METERS")
    private Float detectionRadiusInMeters;
}
