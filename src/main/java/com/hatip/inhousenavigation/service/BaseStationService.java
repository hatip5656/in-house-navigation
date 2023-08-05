package com.hatip.inhousenavigation.service;

import com.hatip.inhousenavigation.model.entity.BaseStationEntity;
import com.hatip.inhousenavigation.model.mapper.BaseStationMapper;
import com.hatip.inhousenavigation.model.pojo.BaseStation;
import com.hatip.inhousenavigation.repository.BaseStationRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseStationService {
    private final BaseStationRepository repository;
    private final BaseStationMapper mapper;


    public BaseStation saveBaseStation(BaseStation baseStation) {
        BaseStationEntity baseStationEntity = mapper.convertToEntity(baseStation);
        return mapper.convertToDto(repository.save(baseStationEntity));
    }

    public List<BaseStation> getAllBaseStations() {
        List<BaseStationEntity> baseStationEntities = repository.findAll();
        return baseStationEntities.stream()
                .map(mapper::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<BaseStation> getBaseStationById(UUID id) {
        return repository.findById(id).map(mapper::convertToDto);
    }

    public void deleteBaseStation(UUID id) {
        repository.deleteById(id);
    }

}
