package com.politecnico.polibus.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.politecnico.polibus.backend.model.GpsData;
import com.politecnico.polibus.backend.service.GpsService;

@RestController
@RequestMapping("/api/v1/gps")
public class GpsController {
    @Autowired
    private GpsService gpsService;

    @PostMapping("/location")
    public ResponseEntity<String> receivedLocation(@RequestBody GpsData gpsData) {
        try {
            gpsService.saveGpsData(gpsData);
            return ResponseEntity.ok("GPS data received and saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Processing GPS data");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllGpsData() {
        return ResponseEntity.ok(gpsService.getAllGpsData());
    }
}
