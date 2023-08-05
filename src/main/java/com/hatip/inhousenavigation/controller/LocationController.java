package com.hatip.inhousenavigation.controller;

import com.hatip.inhousenavigation.model.pojo.Location;
import com.hatip.inhousenavigation.service.ReportService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {
    private final ReportService reportService;

    @GetMapping("/{mobileStationId}")
    public ResponseEntity<Location> getLocationByMobileStationId(@PathVariable UUID mobileStationId) {
        Location locationOfMobileStation = reportService.getLocationOfMobileStation(mobileStationId);
        return ResponseEntity.ok(locationOfMobileStation);
    }

}
