package com.hatip.inhousenavigation.model.pojo;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {
    private UUID mobileId;
    private Float x;
    private Float y;
    private Float errorRadius;
    private Integer errorCode;
    private String errorDescription;
}
