package com.hatip.inhousenavigation.controller;

import com.hatip.inhousenavigation.model.pojo.BaseStation;
import com.hatip.inhousenavigation.service.BaseStationService;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BaseStationControllerTest {

    @Mock
    private BaseStationService baseStationService;

    @InjectMocks
    private BaseStationController baseStationController;

    @Test
    void testCreateBaseStation() throws Exception {
        BaseStation baseStation = new BaseStation();
        baseStation.setId(UUID.randomUUID());
        baseStation.setName("Test Base Station");
        baseStation.setX(10F);
        baseStation.setY(20F);
        baseStation.setDetectionRadiusInMeters(100F);

        when(baseStationService.saveBaseStation(any(BaseStation.class))).thenReturn(baseStation);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(baseStationController).build();

        mockMvc.perform(post("/base-station")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Base Station\",\"x\":10,\"y\":20,\"detectionRadiusInMeters\":100}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Base Station"))
                .andExpect(jsonPath("$.x").value(10))
                .andExpect(jsonPath("$.y").value(20))
                .andExpect(jsonPath("$.detectionRadiusInMeters").value(100));

        verify(baseStationService, times(1)).saveBaseStation(any(BaseStation.class));
    }

    @Test
    void testGetAllBaseStations() throws Exception {
        BaseStation baseStation1 = new BaseStation();
        baseStation1.setId(UUID.randomUUID());
        baseStation1.setName("Base Station 1");
        baseStation1.setX(10F);
        baseStation1.setY(20F);
        baseStation1.setDetectionRadiusInMeters(100F);

        BaseStation baseStation2 = new BaseStation();
        baseStation2.setId(UUID.randomUUID());
        baseStation2.setName("Base Station 2");
        baseStation2.setX(30F);
        baseStation2.setY(40F);
        baseStation2.setDetectionRadiusInMeters(150F);

        List<BaseStation> baseStations = Arrays.asList(baseStation1, baseStation2);

        when(baseStationService.getAllBaseStations()).thenReturn(baseStations);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(baseStationController).build();

        mockMvc.perform(get("/base-station"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").value("Base Station 1"))
                .andExpect(jsonPath("$[0].x").value(10))
                .andExpect(jsonPath("$[0].y").value(20))
                .andExpect(jsonPath("$[0].detectionRadiusInMeters").value(100))
                .andExpect(jsonPath("$[1].id").exists())
                .andExpect(jsonPath("$[1].name").value("Base Station 2"))
                .andExpect(jsonPath("$[1].x").value(30))
                .andExpect(jsonPath("$[1].y").value(40))
                .andExpect(jsonPath("$[1].detectionRadiusInMeters").value(150));

        verify(baseStationService, times(1)).getAllBaseStations();
    }

    // Add more test methods for other endpoints (getBaseStationById, updateBaseStation, deleteBaseStation) similarly.
}
