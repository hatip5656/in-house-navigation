package com.hatip.inhousenavigation.model.pojo;

import java.util.UUID;
import lombok.Data;

@Data
public class MobileStation {
    private UUID id;
    private Double lastKnownX;
    private Double lastKnownY;
}
