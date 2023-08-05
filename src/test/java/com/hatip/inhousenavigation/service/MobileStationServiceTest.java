package com.hatip.inhousenavigation.service;

import com.hatip.inhousenavigation.model.entity.MobileStationEntity;
import com.hatip.inhousenavigation.model.mapper.MobileStationMapper;
import com.hatip.inhousenavigation.model.pojo.MobileStation;
import com.hatip.inhousenavigation.repository.MobileStationRepository;
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

class MobileStationServiceTest {

    private MobileStationService mobileStationService;

    @Mock
    private MobileStationRepository mobileStationRepository;

    @Mock
    private MobileStationMapper mobileStationMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        mobileStationService = new MobileStationService(mobileStationRepository, mobileStationMapper);
    }

    @Test
    void testSave() {
        // Arrange
        MobileStation mobileStation = new MobileStation();
        MobileStationEntity mobileStationEntity = new MobileStationEntity();
        when(mobileStationMapper.convertToEntity(mobileStation)).thenReturn(mobileStationEntity);
        when(mobileStationRepository.save(mobileStationEntity)).thenReturn(mobileStationEntity);
        when(mobileStationMapper.convertToDto(mobileStationEntity)).thenReturn(mobileStation);

        // Act
        MobileStation savedMobileStation = mobileStationService.save(mobileStation);

        // Assert
        Assertions.assertEquals(mobileStation, savedMobileStation);
        verify(mobileStationRepository, times(1)).save(mobileStationEntity);
    }

    @Test
    void testGetAll() {
        // Arrange
        List<MobileStationEntity> mobileStationEntities = new ArrayList<>();
        when(mobileStationRepository.findAll()).thenReturn(mobileStationEntities);
        when(mobileStationMapper.convertToDto(any(MobileStationEntity.class))).thenReturn(new MobileStation());

        // Act
        List<MobileStation> mobileStations = mobileStationService.getAll();

        // Assert
        Assertions.assertEquals(mobileStationEntities.size(), mobileStations.size());
        verify(mobileStationRepository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        // Arrange
        UUID id = UUID.randomUUID();
        MobileStationEntity mobileStationEntity = new MobileStationEntity();
        when(mobileStationRepository.findById(id)).thenReturn(Optional.of(mobileStationEntity));
        when(mobileStationMapper.convertToDto(mobileStationEntity)).thenReturn(new MobileStation());

        // Act
        Optional<MobileStation> mobileStation = mobileStationService.getById(id);

        // Assert
        Assertions.assertTrue(mobileStation.isPresent());
        verify(mobileStationRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteById() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        mobileStationService.deleteById(id);

        // Assert
        verify(mobileStationRepository, times(1)).deleteById(id);
    }
}