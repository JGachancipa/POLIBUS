package com.politecnico.polibus.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.politecnico.polibus.backend.config.SecurityConfig;
import com.politecnico.polibus.backend.model.GpsData;
import com.politecnico.polibus.backend.security.JwtUtil;
import com.politecnico.polibus.backend.service.GpsService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Arrays;

@WebMvcTest(GpsController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
public class GpsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GpsService gpsService;

    @MockBean
    private JwtUtil jwtUtil;

    private GpsData gpsData;

    @BeforeEach
    void setUp() {
        gpsData = new GpsData();
        gpsData.setId(1);
        gpsData.setLongitude(-78.0060);
        gpsData.setLatitude(40.7128);

        when(gpsService.getAllGpsData()).thenReturn(
                Arrays.asList(
                        new GpsData(1, 40.7128, -74.0060),
                        new GpsData(2, 34.0522, -118.2437)));

        doNothing().when(gpsService).saveOrUpdateGpsData(anyList());

        when(jwtUtil.validateToken(any())).thenReturn(true);
        when(jwtUtil.extractUsername(any())).thenReturn("user");
    }

    @Test
    @WithMockUser(roles = "USER")
    void testReceivedLocation_Success() throws Exception {
        mockMvc.perform(post("/api/v1/gps/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"id\":1, \"latitude\":40.7128, \"longitude\":-74.0060}, {\"id\":2, \"latitude\":34.0522, \"longitude\":-118.2437}]"))
                .andExpect(status().isOk())
                .andExpect(content().string("GPS data received and saved/updated successfully"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "test")
    void testReceivedLocation_Failure() throws Exception {
        doThrow(new RuntimeException("Error")).when(gpsService).saveOrUpdateGpsData(anyList());

        mockMvc.perform(post("/api/v1/gps/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"id\":1, \"latitude\":40.7128, \"longitude\":-74.0060}, {\"id\":2, \"latitude\":34.0522, \"longitude\":-118.2437}]"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error processing GPS data"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetAllGpsData() throws Exception {
        String expectedJson = "[{\"id\":1, \"latitude\":40.7128, \"longitude\":-74.0060},{\"id\":2, \"latitude\":34.0522, \"longitude\":-118.2437}]";

        mockMvc.perform(get("/api/v1/gps/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson))
                .andDo(print());
    }
}
