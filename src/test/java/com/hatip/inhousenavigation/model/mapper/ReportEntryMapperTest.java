package com.hatip.inhousenavigation.model.mapper;

import com.hatip.inhousenavigation.model.entity.MobileStationEntity;
import com.hatip.inhousenavigation.model.entity.ReportEntryEntity;
import com.hatip.inhousenavigation.model.pojo.ReportEntry;
import java.sql.Timestamp;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReportEntryMapperTest {

    @Test
    void testConvertToEntity() {
        // Arrange
        ReportEntry reportEntry = new ReportEntry();
        reportEntry.setMobileStationId(UUID.randomUUID());
        reportEntry.setDistance(10.0);
        reportEntry.setTimestamp(new Timestamp(System.currentTimeMillis()));

        // Act
        ReportEntryEntity reportEntryEntity = ReportEntryMapper.convertToEntity(reportEntry);

        // Assert
        Assertions.assertEquals(reportEntry.getDistance(), reportEntryEntity.getDistance());
        Assertions.assertEquals(reportEntry.getTimestamp(), reportEntryEntity.getTimestamp());
    }

    @Test
    void testConvertToDto() {
        // Arrange
        ReportEntryEntity reportEntryEntity = new ReportEntryEntity();
        reportEntryEntity.setMobileStation(new MobileStationEntity());
        reportEntryEntity.setDistance(10.0);
        reportEntryEntity.setTimestamp(new Timestamp(System.currentTimeMillis()));

        // Act
        ReportEntry reportEntry = ReportEntryMapper.convertToDto(reportEntryEntity);

        // Assert
        Assertions.assertEquals(reportEntryEntity.getMobileStation().getId(), reportEntry.getMobileStationId());
        Assertions.assertEquals(reportEntryEntity.getDistance(), reportEntry.getDistance());
        Assertions.assertEquals(reportEntryEntity.getTimestamp(), reportEntry.getTimestamp());
    }
}