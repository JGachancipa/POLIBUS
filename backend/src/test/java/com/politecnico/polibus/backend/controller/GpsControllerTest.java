package com.politecnico.polibus.backend.controller;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.politecnico.polibus.backend.model.GpsData;
import com.politecnico.polibus.backend.service.GpsService;

@WebMvcTest(GpsController.class)
public class GpsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GpsService gpsService;

    private GpsData gpsData;

    @BeforeEach
    void setUp() {
        gpsData = new GpsData();
        gpsData.setLongitude(-78.0060);
        gpsData.setAltitude(10);
        gpsData.setLatitude(40.7128);

        when(gpsService.getAllGpsData()).thenReturn(
                Arrays.asList(
                        new GpsData(40.7128, -74.0060, 10.0),
                        new GpsData(34.0522, -118.2437, 20.0)));
    }

    @Test
    void testReceivedLocation_Success() throws Exception {
        mockMvc.perform(post("/api/v1/gps/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"latitude\":40.7128, \"longitude\":-74.0060, \"altitude\": 10}"))
                .andExpect(status().isOk())
                .andExpect(content().string("GPS data received and saved successfully"))
                .andDo(print());
    }

    @Test
    void testReceivedLocation_Failure() throws Exception {
        doThrow(new RuntimeException("Error")).when(gpsService).saveGpsData(any(GpsData.class));
        mockMvc.perform(post("/api/v1/gps/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"latitude\":40.7128, \"longitude\":-78.0060, \"altitude\": 10}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error Processing GPS data"))
                .andDo(print());
    }

    @Test
    void testGetAllGpsData() throws Exception {
        String expectedJson = "[{\"latitude\":40.7128, \"longitude\":-74.0060, \"altitude\":10.0},{\"latitude\":34.0522, \"longitude\":-118.2437, \"altitude\":20.0}]";

        mockMvc.perform(get("/api/v1/gps/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson))
                .andDo(print());
    }

}
