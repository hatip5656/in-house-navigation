package com.hatip.inhousenavigation.model.pojo;

import java.sql.Timestamp;
import java.util.UUID;
import lombok.Data;

@Data
public class ReportEntry {
    private UUID mobileStationId;
    private Double distance;
    private Timestamp timestamp;
}