package com.hatip.inhousenavigation.controller;

import com.hatip.inhousenavigation.model.pojo.Location;
import com.hatip.inhousenavigation.service.ReportService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private LocationController locationController;

    @Test
    void testGetLocationByMobileStationId() throws Exception {
        UUID mobileStationId = UUID.randomUUID();
        Location location = Location.builder()
                .mobileId(mobileStationId)
                .x(10F)
                .y(20F)
                .build();
        when(reportService.getLocationOfMobileStation(mobileStationId)).thenReturn(location);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();

        mockMvc.perform(get("/location/{mobileStationId}", mobileStationId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mobileId").value(mobileStationId.toString()))
                .andExpect(jsonPath("$.x").value(10))
                .andExpect(jsonPath("$.y").value(20));

        verify(reportService, times(1)).getLocationOfMobileStation(mobileStationId);
    }

}
