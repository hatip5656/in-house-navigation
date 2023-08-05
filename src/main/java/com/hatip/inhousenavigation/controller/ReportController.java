package com.hatip.inhousenavigation.controller;

import com.hatip.inhousenavigation.model.pojo.Report;
import com.hatip.inhousenavigation.model.pojo.ReportEntry;
import com.hatip.inhousenavigation.service.ReportService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService service;

    @PostMapping
    public ResponseEntity<List<ReportEntry>> create(@RequestBody Report report) {

        List<ReportEntry> reportEntries = service.saveAll(report.getReports(), report.getBaseStationId());
        return new ResponseEntity<>(reportEntries, HttpStatus.CREATED);
    }
}
