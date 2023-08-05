package com.hatip.inhousenavigation.model.mapper;

import com.hatip.inhousenavigation.model.entity.BaseStationEntity;
import com.hatip.inhousenavigation.model.pojo.BaseStation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BaseStationMapper {

    private final ModelMapper modelMapper;

    public BaseStationMapper() {
        this.modelMapper = new ModelMapper();
    }

    public BaseStationEntity convertToEntity(BaseStation baseStation) {
        return modelMapper.map(baseStation, BaseStationEntity.class);
    }

    public BaseStation convertToDto(BaseStationEntity baseStationEntity) {
        return modelMapper.map(baseStationEntity, BaseStation.class);
    }
}

