package com.hatip.inhousenavigation.repository;

import com.hatip.inhousenavigation.model.entity.ReportEntryEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportEntryRepository extends JpaRepository<ReportEntryEntity, UUID> {
    @Query("SELECT re FROM ReportEntryEntity re " +
            "LEFT JOIN ReportEntryEntity re2 " +
            "ON re.baseStation.id = re2.baseStation.id AND re.timestamp < re2.timestamp " +
            "WHERE re.mobileStation.id = :mobileStationId AND re2.id IS NULL " +
            "ORDER BY re.timestamp DESC")
    List<ReportEntryEntity> findLast3RecordsWithDistinctBaseStationId(UUID mobileStationId);
}
