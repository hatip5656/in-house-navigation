package com.hatip.inhousenavigation.model.pojo;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class Report {
    private UUID baseStationId;
    private List<ReportEntry> reports;
}
