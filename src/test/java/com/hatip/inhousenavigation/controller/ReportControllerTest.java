package com.hatip.inhousenavigation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatip.inhousenavigation.model.pojo.Report;
import com.hatip.inhousenavigation.model.pojo.ReportEntry;
import com.hatip.inhousenavigation.service.ReportService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }

    @Test
    void testCreateReport() throws Exception {
        // Prepare test data
        UUID baseStationId = UUID.randomUUID();

        List<ReportEntry> reportEntries = new ArrayList<>();
        ReportEntry entry1 = new ReportEntry();
        entry1.setMobileStationId(UUID.randomUUID());
        entry1.setDistance(10.0);
        entry1.setTimestamp(new Timestamp(System.currentTimeMillis()));
        reportEntries.add(entry1);

        ReportEntry entry2 = new ReportEntry();
        entry2.setMobileStationId(UUID.randomUUID());
        entry2.setDistance(15.0);
        entry2.setTimestamp(new Timestamp(System.currentTimeMillis()));
        reportEntries.add(entry2);

        Report report = new Report();
        report.setBaseStationId(baseStationId);
        report.setReports(reportEntries);

        // Mock the service method
        when(reportService.saveAll(anyList(), any(UUID.class))).thenReturn(reportEntries);

        // Perform the POST request
        MvcResult result = mockMvc.perform(post("/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(report)))
                .andExpect(status().isCreated())
                .andReturn();

        // Get the response content as a JSON string
        String responseContent = result.getResponse().getContentAsString();

        // Verify the timestamps using the Unix timestamp format (milliseconds since January 1, 1970)
        // You can use a custom Matcher to compare timestamps within a certain threshold
        assertThat(responseContent, containsString(String.valueOf(entry1.getTimestamp().getTime())));
        assertThat(responseContent, containsString(String.valueOf(entry2.getTimestamp().getTime())));

        // Verify the service method was called
        verify(reportService, times(1)).saveAll(anyList(), any(UUID.class));
    }

    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
