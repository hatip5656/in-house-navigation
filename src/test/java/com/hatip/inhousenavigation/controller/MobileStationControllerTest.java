package com.hatip.inhousenavigation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatip.inhousenavigation.model.pojo.MobileStation;
import com.hatip.inhousenavigation.service.MobileStationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MobileStationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MobileStationService mobileStationService;

    @InjectMocks
    private MobileStationController mobileStationController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mobileStationController).build();
    }

    @Test
    void testCreateMobileStation() throws Exception {
        // Prepare test data
        MobileStation mobileStation = new MobileStation();
        mobileStation.setLastKnownX(10.0);
        mobileStation.setLastKnownY(20.0);

        MobileStation savedMobileStation = new MobileStation();
        savedMobileStation.setId(UUID.randomUUID());
        savedMobileStation.setLastKnownX(10.0);
        savedMobileStation.setLastKnownY(20.0);

        // Mock the service method
        when(mobileStationService.save(any(MobileStation.class))).thenReturn(savedMobileStation);

        // Perform the POST request
        mockMvc.perform(post("/mobile-station")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mobileStation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.lastKnownX").value(savedMobileStation.getLastKnownX()))
                .andExpect(jsonPath("$.lastKnownY").value(savedMobileStation.getLastKnownY()));

        // Verify the service method was called
        verify(mobileStationService, times(1)).save(any(MobileStation.class));
    }

    @Test
    void testGetAllMobileStations() throws Exception {
        // Prepare test data
        List<MobileStation> mobileStations = new ArrayList<>();
        MobileStation mobileStation1 = new MobileStation();
        mobileStation1.setId(UUID.randomUUID());
        mobileStation1.setLastKnownX(10.0);
        mobileStation1.setLastKnownY(20.0);
        mobileStations.add(mobileStation1);

        MobileStation mobileStation2 = new MobileStation();
        mobileStation2.setId(UUID.randomUUID());
        mobileStation2.setLastKnownX(30.0);
        mobileStation2.setLastKnownY(40.0);
        mobileStations.add(mobileStation2);

        // Mock the service method
        when(mobileStationService.getAll()).thenReturn(mobileStations);

        // Perform the GET request
        mockMvc.perform(get("/mobile-station"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(mobileStation1.getId().toString()))
                .andExpect(jsonPath("$[0].lastKnownX").value(mobileStation1.getLastKnownX()))
                .andExpect(jsonPath("$[0].lastKnownY").value(mobileStation1.getLastKnownY()))
                .andExpect(jsonPath("$[1].id").value(mobileStation2.getId().toString()))
                .andExpect(jsonPath("$[1].lastKnownX").value(mobileStation2.getLastKnownX()))
                .andExpect(jsonPath("$[1].lastKnownY").value(mobileStation2.getLastKnownY()));

        // Verify the service method was called
        verify(mobileStationService, times(1)).getAll();
    }

    @Test
    void testGetMobileStationById() throws Exception {
        // Prepare test data
        UUID id = UUID.randomUUID();
        MobileStation mobileStation = new MobileStation();
        mobileStation.setId(id);
        mobileStation.setLastKnownX(10.0);
        mobileStation.setLastKnownY(20.0);

        // Mock the service method
        when(mobileStationService.getById(id)).thenReturn(Optional.of(mobileStation));

        // Perform the GET request
        mockMvc.perform(get("/mobile-station/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.lastKnownX").value(mobileStation.getLastKnownX()))
                .andExpect(jsonPath("$.lastKnownY").value(mobileStation.getLastKnownY()));

        // Verify the service method was called
        verify(mobileStationService, times(1)).getById(id);
    }

    @Test
    void testGetMobileStationByIdNotFound() throws Exception {
        // Prepare test data
        UUID id = UUID.randomUUID();

        // Mock the service method
        when(mobileStationService.getById(id)).thenReturn(Optional.empty());

        // Perform the GET request
        mockMvc.perform(get("/mobile-station/{id}", id))
                .andExpect(status().isNotFound());

        // Verify the service method was called
        verify(mobileStationService, times(1)).getById(id);
    }

    @Test
    void testUpdateMobileStation() throws Exception {
        // Prepare test data
        UUID id = UUID.randomUUID();
        MobileStation mobileStation = new MobileStation();
        mobileStation.setLastKnownX(50.0);
        mobileStation.setLastKnownY(60.0);

        MobileStation existingMobileStation = new MobileStation();
        existingMobileStation.setId(id);
        existingMobileStation.setLastKnownX(10.0);
        existingMobileStation.setLastKnownY(20.0);

        MobileStation updatedMobileStation = new MobileStation();
        updatedMobileStation.setId(id);
        updatedMobileStation.setLastKnownX(50.0);
        updatedMobileStation.setLastKnownY(60.0);

        // Mock the service method
        when(mobileStationService.getById(id)).thenReturn(Optional.of(existingMobileStation));
        when(mobileStationService.save(any(MobileStation.class))).thenReturn(updatedMobileStation);

        // Perform the PUT request
        mockMvc.perform(put("/mobile-station/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mobileStation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.lastKnownX").value(updatedMobileStation.getLastKnownX()))
                .andExpect(jsonPath("$.lastKnownY").value(updatedMobileStation.getLastKnownY()));

        // Verify the service method was called
        verify(mobileStationService, times(1)).getById(id);
        verify(mobileStationService, times(1)).save(any(MobileStation.class));
    }

    @Test
    void testUpdateMobileStationNotFound() throws Exception {
        // Prepare test data
        UUID id = UUID.randomUUID();
        MobileStation mobileStation = new MobileStation();
        mobileStation.setLastKnownX(50.0);
        mobileStation.setLastKnownY(60.0);

        // Mock the service method
        when(mobileStationService.getById(id)).thenReturn(Optional.empty());

        // Perform the PUT request
        mockMvc.perform(put("/mobile-station/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mobileStation)))
                .andExpect(status().isNotFound());

        // Verify the service method was called
        verify(mobileStationService, times(1)).getById(id);
        verify(mobileStationService, never()).save(any(MobileStation.class));
    }

    @Test
    void testDeleteMobileStation() throws Exception {
        // Prepare test data
        UUID id = UUID.randomUUID();

        // Mock the service method
        when(mobileStationService.getById(id)).thenReturn(Optional.of(new MobileStation()));

        // Perform the DELETE request
        mockMvc.perform(delete("/mobile-station/{id}", id))
                .andExpect(status().isNoContent());

        // Verify the service method was called
        verify(mobileStationService, times(1)).getById(id);
        verify(mobileStationService, times(1)).deleteById(id);
    }

    @Test
    void testDeleteMobileStationNotFound() throws Exception {
        // Prepare test data
        UUID id = UUID.randomUUID();

        // Mock the service method
        when(mobileStationService.getById(id)).thenReturn(Optional.empty());

        // Perform the DELETE request
        mockMvc.perform(delete("/mobile-station/{id}", id))
                .andExpect(status().isNotFound());

        // Verify the service method was called
        verify(mobileStationService, times(1)).getById(id);
        verify(mobileStationService, never()).deleteById(id);
    }

    // Utility method to convert object to JSON string
    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


