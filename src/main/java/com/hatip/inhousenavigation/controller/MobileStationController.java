package com.hatip.inhousenavigation.controller;

import com.hatip.inhousenavigation.model.pojo.MobileStation;
import com.hatip.inhousenavigation.service.MobileStationService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile-station")
@RequiredArgsConstructor
public class MobileStationController {
    private final MobileStationService service;

    @PostMapping
    public ResponseEntity<MobileStation> createMobileStation(@RequestBody MobileStation mobileStation) {
        MobileStation savedMobileStation = service.save(mobileStation);
        return new ResponseEntity<>(savedMobileStation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MobileStation>> getAllMobileStations() {
        List<MobileStation> mobileStations = service.getAll();
        return new ResponseEntity<>(mobileStations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MobileStation> getMobileStationById(@PathVariable UUID id) {
        Optional<MobileStation> mobileStation = service.getById(id);
        return mobileStation.map(station -> new ResponseEntity<>(station, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MobileStation> updateMobileStation(@PathVariable UUID id, @RequestBody MobileStation mobileStation) {
        Optional<MobileStation> existingMobileStation = service.getById(id);
        if (existingMobileStation.isPresent()) {
            mobileStation.setId(id);
            MobileStation updatedMobileStation = service.save(mobileStation);
            return new ResponseEntity<>(updatedMobileStation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMobileStation(@PathVariable UUID id) {
        Optional<MobileStation> mobileStation = service.getById(id);
        if (mobileStation.isPresent()) {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
