package com.hatip.inhousenavigation.repository;

import com.hatip.inhousenavigation.model.entity.MobileStationEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileStationRepository extends JpaRepository<MobileStationEntity, UUID> {
}
