package com.politecnico.polibus.backend.controller;

import com.politecnico.polibus.backend.model.GpsData;
import com.politecnico.polibus.backend.service.GpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/gps")
public class GpsController {

    private static final Logger logger = LoggerFactory.getLogger(GpsController.class);

    @Autowired
    private GpsService gpsService;

    @PostMapping("/location")
    public ResponseEntity<String> receivedLocations(@RequestBody List<GpsData> gpsDataList) {
        logger.info("Received GPS data: {}", gpsDataList);
        try {
            gpsService.saveOrUpdateGpsData(gpsDataList);
            return ResponseEntity.ok("GPS data received and saved/updated successfully");
        } catch (Exception e) {
            logger.error("Error processing GPS data: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing GPS data");
        }
    }
    

    @GetMapping("/all")
    public ResponseEntity<?> getAllGpsData() {
        logger.info("Fetching all GPS data");
        return ResponseEntity.ok(gpsService.getAllGpsData());
    }
}
