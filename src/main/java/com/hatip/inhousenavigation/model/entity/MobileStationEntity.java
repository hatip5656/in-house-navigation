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
@Table(name = "MOBILE_STATION_ENTITY")
public class MobileStationEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(name = "ID")
    private UUID id;
    @Column(name = "LAST_KNOWNX")
    private Double lastKnownX;
    @Column(name = "LAST_KNOWNY")
    private Double lastKnownY;
}
