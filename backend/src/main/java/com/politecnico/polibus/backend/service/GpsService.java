package com.politecnico.polibus.backend.service;

import java.util.List;

import com.politecnico.polibus.backend.model.GpsData;

public interface GpsService {
    void removeInactiveDevices();
    List<GpsData> getAllGpsData();
    void saveOrUpdateGpsData(List<GpsData> gpsData);
}
