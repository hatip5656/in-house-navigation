package com.hatip.inhousenavigation.controller;

import com.hatip.inhousenavigation.model.pojo.BaseStation;
import com.hatip.inhousenavigation.service.BaseStationService;
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
@RequestMapping("/base-station")
@RequiredArgsConstructor
public class BaseStationController {
    private final BaseStationService service;

    @PostMapping
    public ResponseEntity<BaseStation> createBaseStation(@RequestBody BaseStation baseStation) {
        BaseStation savedBaseStation = service.saveBaseStation(baseStation);
        return new ResponseEntity<>(savedBaseStation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BaseStation>> getAllBaseStations() {
        List<BaseStation> baseStations = service.getAllBaseStations();
        return new ResponseEntity<>(baseStations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseStation> getBaseStationById(@PathVariable UUID id) {
        Optional<BaseStation> baseStation = service.getBaseStationById(id);
        return baseStation.map(station -> new ResponseEntity<>(station, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseStation> updateBaseStation(@PathVariable UUID id, @RequestBody BaseStation baseStation) {
        Optional<BaseStation> existingBaseStation = service.getBaseStationById(id);
        if (existingBaseStation.isPresent()) {
            baseStation.setId(id);
            BaseStation updatedBaseStation = service.saveBaseStation(baseStation);
            return new ResponseEntity<>(updatedBaseStation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaseStation(@PathVariable UUID id) {
        Optional<BaseStation> baseStation = service.getBaseStationById(id);
        if (baseStation.isPresent()) {
            service.deleteBaseStation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
