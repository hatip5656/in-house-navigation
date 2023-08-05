package com.hatip.inhousenavigation.model.pojo;

import java.util.UUID;
import lombok.Data;

@Data
public class BaseStation {
    private UUID id;
    private String name;
    private Float x;
    private Float y;
    private Float detectionRadiusInMeters;
}

