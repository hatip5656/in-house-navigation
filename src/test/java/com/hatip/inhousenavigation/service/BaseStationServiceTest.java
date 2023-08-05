package com.hatip.inhousenavigation.service;


import com.hatip.inhousenavigation.model.entity.BaseStationEntity;
import com.hatip.inhousenavigation.model.mapper.BaseStationMapper;
import com.hatip.inhousenavigation.model.pojo.BaseStation;
import com.hatip.inhousenavigation.repository.BaseStationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BaseStationServiceTest {

    private BaseStationService baseStationService;

    @Mock
    private BaseStationRepository baseStationRepository;

    @Mock
    private BaseStationMapper baseStationMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        baseStationService = new BaseStationService(baseStationRepository, baseStationMapper);
    }

    @Test
    void testSaveBaseStation() {
        // Arrange
        BaseStation baseStation = new BaseStation();
        BaseStationEntity baseStationEntity = new BaseStationEntity();
        when(baseStationMapper.convertToEntity(baseStation)).thenReturn(baseStationEntity);
        when(baseStationRepository.save(baseStationEntity)).thenReturn(baseStationEntity);
        when(baseStationMapper.convertToDto(baseStationEntity)).thenReturn(baseStation);

        // Act
        BaseStation savedBaseStation = baseStationService.saveBaseStation(baseStation);

        // Assert
        Assertions.assertEquals(baseStation, savedBaseStation);
        verify(baseStationRepository, times(1)).save(baseStationEntity);
    }

    @Test
    void testGetAllBaseStations() {
        // Arrange
        List<BaseStationEntity> baseStationEntities = new ArrayList<>();
        when(baseStationRepository.findAll()).thenReturn(baseStationEntities);
        when(baseStationMapper.convertToDto(any(BaseStationEntity.class))).thenReturn(new BaseStation());

        // Act
        List<BaseStation> baseStations = baseStationService.getAllBaseStations();

        // Assert
        Assertions.assertEquals(baseStationEntities.size(), baseStations.size());
        verify(baseStationRepository, times(1)).findAll();
    }

    @Test
    void testGetBaseStationById() {
        // Arrange
        UUID id = UUID.randomUUID();
        BaseStationEntity baseStationEntity = new BaseStationEntity();
        when(baseStationRepository.findById(id)).thenReturn(Optional.of(baseStationEntity));
        when(baseStationMapper.convertToDto(baseStationEntity)).thenReturn(new BaseStation());

        // Act
        Optional<BaseStation> baseStation = baseStationService.getBaseStationById(id);

        // Assert
        Assertions.assertTrue(baseStation.isPresent());
        verify(baseStationRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteBaseStation() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        baseStationService.deleteBaseStation(id);

        // Assert
        verify(baseStationRepository, times(1)).deleteById(id);
    }
}