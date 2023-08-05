package com.hatip.inhousenavigation.model.mapper;

import com.hatip.inhousenavigation.model.entity.MobileStationEntity;
import com.hatip.inhousenavigation.model.pojo.MobileStation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MobileStationMapper {

    private final ModelMapper modelMapper;

    public MobileStationMapper() {
        this.modelMapper = new ModelMapper();
    }

    public MobileStationEntity convertToEntity(MobileStation mobileStation) {
        return modelMapper.map(mobileStation, MobileStationEntity.class);
    }

    public MobileStation convertToDto(MobileStationEntity mobileStationEntity) {
        return modelMapper.map(mobileStationEntity, MobileStation.class);
    }
}

