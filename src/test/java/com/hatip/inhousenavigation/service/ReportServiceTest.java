package com.hatip.inhousenavigation.service;

import com.hatip.inhousenavigation.exception.DistanceExceedsDetectionRadiusException;
import com.hatip.inhousenavigation.model.entity.MobileStationEntity;
import com.hatip.inhousenavigation.model.entity.ReportEntryEntity;
import com.hatip.inhousenavigation.model.mapper.BaseStationMapper;
import com.hatip.inhousenavigation.model.pojo.BaseStation;
import com.hatip.inhousenavigation.model.pojo.Location;
import com.hatip.inhousenavigation.model.pojo.ReportEntry;
import com.hatip.inhousenavigation.repository.ReportEntryRepository;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReportServiceTest {

    private ReportService reportService;

    @Mock
    private ReportEntryRepository reportEntryRepository;

    @Mock
    private MobileStationService mobileStationService;

    @Mock
    private BaseStationService baseStationService;

    private BaseStationMapper baseStationMapper;

    @BeforeEach
    void setup() {
        baseStationMapper = new BaseStationMapper();
        MockitoAnnotations.initMocks(this);
        reportService = new ReportService(reportEntryRepository, mobileStationService, baseStationService, baseStationMapper);
    }

    @Test
    void testSaveAll() {
        List<ReportEntry> entries = new ArrayList<>();
        UUID reporterId = UUID.randomUUID();

        when(reportEntryRepository.save(any(ReportEntryEntity.class))).thenReturn(new ReportEntryEntity());

        List<ReportEntry> savedEntries = reportService.saveAll(entries, reporterId);

        assertEquals(entries.size(), savedEntries.size());
        verify(reportEntryRepository, times(entries.size())).save(any(ReportEntryEntity.class));
    }

    @Test
    void testSave() {
        ReportEntry reportEntry = new ReportEntry();
        reportEntry.setDistance(15.00);
        Timestamp now = Timestamp.from(Instant.now());
        reportEntry.setTimestamp(now);
        UUID reporterId = UUID.randomUUID();
        BaseStation baseStation = new BaseStation();
        baseStation.setId(reporterId);
        baseStation.setDetectionRadiusInMeters(100F);
        baseStation.setName("test");
        MobileStationEntity mobileStation = generateMobileStationEntity();
        ReportEntryEntity reportEntryEntity = generateReportEntity(now, baseStation, mobileStation);
        reportEntryEntity.setDistance(15.00);

        when(baseStationService.getBaseStationById(reporterId)).thenReturn(Optional.of(baseStation));

        when(reportEntryRepository.save(any(ReportEntryEntity.class))).thenReturn(reportEntryEntity);

        when(reportEntryRepository.findLast3RecordsWithDistinctBaseStationId(any())).thenReturn(Arrays.asList(reportEntryEntity));

        ReportEntry savedReportEntry = reportService.save(reportEntry, reporterId);
        assertEquals(reportEntry.getDistance(), savedReportEntry.getDistance());
        assertEquals(reportEntry.getTimestamp(), savedReportEntry.getTimestamp());
        verify(reportEntryRepository, times(1)).save(any(ReportEntryEntity.class));
    }

    @Test
    void testSaveThrowsDetectionRadiusExceeded() {
        ReportEntry reportEntry = new ReportEntry();
        reportEntry.setDistance(25.00);
        Timestamp now = Timestamp.from(Instant.now());
        reportEntry.setTimestamp(now);
        UUID reporterId = UUID.randomUUID();
        BaseStation baseStation = generateBaseStation(reporterId);
        MobileStationEntity mobileStation = generateMobileStationEntity();
        ReportEntryEntity reportEntryEntity = generateReportEntity(now, baseStation, mobileStation);

        when(baseStationService.getBaseStationById(reporterId)).thenReturn(Optional.of(baseStation));

        when(reportEntryRepository.save(any(ReportEntryEntity.class))).thenReturn(reportEntryEntity);

        when(reportEntryRepository.findLast3RecordsWithDistinctBaseStationId(any())).thenReturn(Arrays.asList(reportEntryEntity));

        assertThrows(DistanceExceedsDetectionRadiusException.class,
                () -> reportService.save(reportEntry, reporterId));

    }

    @Test
    void testGetLocationOfMobileStation_WithEnoughData() {
        UUID mobileStationId = UUID.randomUUID();
        Timestamp now = Timestamp.from(Instant.now());

        BaseStation baseStation1 = generateBaseStation(UUID.randomUUID());
        BaseStation baseStation2 = generateBaseStation(UUID.randomUUID());
        BaseStation baseStation3 = generateBaseStation(UUID.randomUUID());
        MobileStationEntity mobileStation = generateMobileStationEntity();
        ReportEntryEntity reportEntryEntity1 = generateReportEntity(now, baseStation1, mobileStation);
        ReportEntryEntity reportEntryEntity2 = generateReportEntity(now, baseStation2, mobileStation);
        ReportEntryEntity reportEntryEntity3 = generateReportEntity(now, baseStation3, mobileStation);
        List<ReportEntryEntity> lastThree = Arrays.asList(reportEntryEntity1, reportEntryEntity2, reportEntryEntity3);

        when(reportEntryRepository.findLast3RecordsWithDistinctBaseStationId(mobileStationId)).thenReturn(lastThree);

        Location location = reportService.getLocationOfMobileStation(mobileStationId);

        assertEquals(mobileStationId, location.getMobileId());
        assertNull(location.getErrorDescription());
    }

    @Test
    void testGetLocationOfMobileStation_WithoutEnoughData() {
        UUID mobileStationId = UUID.randomUUID();

        List<ReportEntryEntity> lastThree = new ArrayList<>();

        when(reportEntryRepository.findLast3RecordsWithDistinctBaseStationId(mobileStationId)).thenReturn(lastThree);

        Location location = reportService.getLocationOfMobileStation(mobileStationId);

        assertEquals(mobileStationId, location.getMobileId());
        assertEquals("there isn't enough data present!", location.getErrorDescription());
    }

    @Test
    void testGetLocationOfMobileStation_Exception() {
        UUID mobileStationId = UUID.randomUUID();

        when(reportEntryRepository.findLast3RecordsWithDistinctBaseStationId(mobileStationId)).thenThrow(new RuntimeException("Test exception"));

        Location location = reportService.getLocationOfMobileStation(mobileStationId);

        assertEquals(mobileStationId, location.getMobileId());
        assertEquals("Test exception", location.getErrorDescription());
    }

    @NotNull
    private BaseStation generateBaseStation(UUID reporterId) {
        BaseStation baseStation = new BaseStation();
        baseStation.setId(reporterId);
        baseStation.setDetectionRadiusInMeters(20F);
        baseStation.setName("test");
        Random random = new Random();
        float randomX = (1f + (100f - 1.0f) * random.nextFloat());
        float randomY = (1f + (100f - 1.0f) * random.nextFloat());
        baseStation.setX(randomX);
        baseStation.setY(randomY);
        return baseStation;
    }

    @NotNull
    private MobileStationEntity generateMobileStationEntity() {
        MobileStationEntity mobileStation = new MobileStationEntity();
        mobileStation.setId(UUID.randomUUID());
        return mobileStation;
    }

    @NotNull
    private ReportEntryEntity generateReportEntity(Timestamp now, BaseStation baseStation, MobileStationEntity mobileStation) {
        ReportEntryEntity reportEntryEntity = new ReportEntryEntity();
        reportEntryEntity.setBaseStation(baseStationMapper.convertToEntity(baseStation));
        Random random = new Random();
        double randomDistance = 1.0 + (50.0 - 1.0) * random.nextDouble();
        reportEntryEntity.setDistance(randomDistance);
        reportEntryEntity.setTimestamp(now);
        reportEntryEntity.setMobileStation(mobileStation);
        return reportEntryEntity;
    }
}