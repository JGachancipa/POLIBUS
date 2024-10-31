package com.politecnico.polibus.backend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.politecnico.polibus.backend.model.GpsData;
import com.politecnico.polibus.backend.service.GpsService;

@Service
public class GpsServiceImpl implements GpsService {

    private final List<GpsData> gpsDataList = new ArrayList<>();

    @Override
    public void saveGpsData(GpsData gpsData) {
        gpsDataList.add(gpsData);
    }

    @Override
    public List<GpsData> getAllGpsData() {
        return gpsDataList;
    }
}
