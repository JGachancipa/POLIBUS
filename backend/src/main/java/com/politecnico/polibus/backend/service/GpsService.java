package com.politecnico.polibus.backend.service;

import java.util.List;

import com.politecnico.polibus.backend.model.GpsData;

public interface GpsService {
    void saveGpsData(GpsData gpsData);
    List<GpsData> getAllGpsData();
}
