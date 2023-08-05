package com.hatip.inhousenavigation.model.entity;

import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "REPORT_ENTRY_ENTITY")
public class ReportEntryEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(name = "ID")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "BASE_STATION_ID")
    private BaseStationEntity baseStation;
    @ManyToOne
    @JoinColumn(name = "MOBILE_STATION_ID")
    private MobileStationEntity mobileStation;
    @Column(name = "DISTANCE")
    private Double distance;
    @Column(name = "TIMESTAMP")
    private Timestamp timestamp;
}