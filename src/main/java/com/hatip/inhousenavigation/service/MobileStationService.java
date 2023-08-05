package com.hatip.inhousenavigation.service;

import com.hatip.inhousenavigation.model.entity.MobileStationEntity;
import com.hatip.inhousenavigation.model.mapper.MobileStationMapper;
import com.hatip.inhousenavigation.model.pojo.MobileStation;
import com.hatip.inhousenavigation.repository.MobileStationRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MobileStationService {
    private final MobileStationRepository repository;
    private final MobileStationMapper mapper;


    public MobileStation save(MobileStation mobileStation) {
        MobileStationEntity mobileStationEntity = mapper.convertToEntity(mobileStation);
        return mapper.convertToDto(repository.save(mobileStationEntity));
    }

    public List<MobileStation> getAll() {
        List<MobileStationEntity> mobileStationEntities = repository.findAll();
        return mobileStationEntities.stream()
                .map(mapper::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<MobileStation> getById(UUID id) {
        return repository.findById(id).map(mapper::convertToDto);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
