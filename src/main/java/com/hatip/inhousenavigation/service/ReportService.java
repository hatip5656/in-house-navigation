package com.hatip.inhousenavigation.service;

import com.hatip.inhousenavigation.exception.DistanceExceedsDetectionRadiusException;
import com.hatip.inhousenavigation.model.entity.ReportEntryEntity;
import com.hatip.inhousenavigation.model.mapper.BaseStationMapper;
import com.hatip.inhousenavigation.model.mapper.ReportEntryMapper;
import com.hatip.inhousenavigation.model.pojo.BaseStation;
import com.hatip.inhousenavigation.model.pojo.Location;
import com.hatip.inhousenavigation.model.pojo.MobileStation;
import com.hatip.inhousenavigation.model.pojo.ReportEntry;
import com.hatip.inhousenavigation.repository.ReportEntryRepository;
import com.hatip.inhousenavigation.utils.CoordinateCalculator;
import com.hatip.inhousenavigation.utils.CoordinateCalculator.CoordinatesAndDistances;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final ReportEntryRepository repository;
    private final MobileStationService mobileStationService;
    private final BaseStationService baseStationService;
    private final BaseStationMapper baseStationMapper;

    public List<ReportEntry> saveAll(List<ReportEntry> entries, UUID reporterId) {
        return entries.stream().map(entry -> save(entry, reporterId))
                .collect(Collectors.toList());
    }

    public ReportEntry save(ReportEntry reportEntry, UUID reporterId) {
        ReportEntryEntity entity = ReportEntryMapper.convertToEntity(reportEntry);
        Optional<BaseStation> baseStation = baseStationService.getBaseStationById(reporterId);

        if (baseStation.isPresent()) {
            entity.setBaseStation(baseStationMapper.convertToEntity(baseStation.get()));
            ReportEntryEntity savedReportEntity = repository.save(entity);

            if (reportEntry.getDistance() > baseStation.get().getDetectionRadiusInMeters()) {
                throw new DistanceExceedsDetectionRadiusException("Distance to Z exceeds the base station detection radius");
            }
            List<ReportEntryEntity> lastThree = findLast3RecordsWithDistinctBaseStationId(savedReportEntity.getMobileStation().getId());
            if (lastThree.size() == 3) {
                double[] coordinatesOfZ = getCoordinatesOfZ(lastThree);

                Optional<MobileStation> mobileStation = mobileStationService.getById(reportEntry.getMobileStationId());
                mobileStation.ifPresent(ms -> {
                    ms.setLastKnownX(coordinatesOfZ[0]);
                    ms.setLastKnownY(coordinatesOfZ[1]);
                    mobileStationService.save(ms);
                });
            }
            return ReportEntryMapper.convertToDto(savedReportEntity);
        } else {
            throw new EmptyResultDataAccessException("BaseStation not found with ID: " + reporterId, 1);
        }
    }

    private static double[] getCoordinatesOfZ(List<ReportEntryEntity> lastThree) {
        double[][] baseStationCoords = new double[3][2];
        double[] distances = new double[3];

        for (int i = 0; i < 3; i++) {
            baseStationCoords[i][0] = lastThree.get(i).getBaseStation().getX();
            baseStationCoords[i][1] = lastThree.get(i).getBaseStation().getY();
            distances[i] = lastThree.get(i).getDistance();
        }
        CoordinatesAndDistances data = CoordinatesAndDistances.builder()
                .xA(baseStationCoords[0][0]).yA(baseStationCoords[0][1])
                .xB(baseStationCoords[1][0]).yB(baseStationCoords[1][1])
                .xC(baseStationCoords[2][0]).yC(baseStationCoords[2][1])
                .distanceToA(distances[0]).distanceToB(distances[1]).distanceToC(distances[2])
                .build();

        return CoordinateCalculator.findCoordinatesOfZ(data);
    }

    public List<ReportEntryEntity> findLast3RecordsWithDistinctBaseStationId(UUID mobileStationId) {
        List<ReportEntryEntity> allRecords = repository.findLast3RecordsWithDistinctBaseStationId(mobileStationId);
        int limit = 3;
        if (allRecords.size() > limit) {
            return allRecords.subList(0, limit);
        } else {
            return allRecords;
        }
    }


    public Location getLocationOfMobileStation(UUID mobileStationId) {
        try {
            List<ReportEntryEntity> lastThree = findLast3RecordsWithDistinctBaseStationId(mobileStationId);
            if (lastThree.size() == 3) {
                double[] coordinatesOfZ = getCoordinatesOfZ(lastThree);
                return Location.builder()
                        .mobileId(mobileStationId)
                        .x((float) coordinatesOfZ[0])
                        .y((float) coordinatesOfZ[1])
                        .errorRadius((float) 0)
                        .build();
            } else {
                return Location.builder()
                        .errorCode(404)
                        .errorDescription("there isn't enough data present!")
                        .mobileId(mobileStationId)
                        .errorRadius((float) 0)
                        .build();

            }
        } catch (Exception e) {
            log.error("Exception while calculating location Ex: {},", e.getMessage());
            return Location.builder()
                    .errorCode(500)
                    .errorDescription(e.getMessage())
                    .mobileId(mobileStationId)
                    .errorRadius((float) 0)
                    .build();
        }
    }
}


