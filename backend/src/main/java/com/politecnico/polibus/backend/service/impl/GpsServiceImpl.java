package com.politecnico.polibus.backend.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.politecnico.polibus.backend.model.GpsData;
import com.politecnico.polibus.backend.service.GpsService;

@Service
public class GpsServiceImpl implements GpsService {

    private final long INACTIVITY_THRESHOLD = 60000;
    private final List<GpsData> gpsDataList = new ArrayList<>();

    @Override
    public void saveOrUpdateGpsData(List<GpsData> gpsDataListInput) {
        gpsDataListInput.forEach(gpsData -> {
            gpsData.setLastUpdate(LocalDateTime.now());

            GpsData existingData = gpsDataList.stream()
                    .filter(data -> data.getId() == gpsData.getId())
                    .findFirst()
                    .orElse(null);

            if (existingData != null) {
                existingData.setLatitude(gpsData.getLatitude());
                existingData.setLongitude(gpsData.getLongitude());
                existingData.setLastUpdate(LocalDateTime.now());
            } else {
                gpsDataList.add(gpsData);
            }
        });
    }

    @Override
    public List<GpsData> getAllGpsData() {
        return gpsDataList;
    }

    @Scheduled(fixedRate = 60000)
    public void removeInactiveDevices() {
        LocalDateTime currenTime = LocalDateTime.now();
        Iterator<GpsData> iterator = gpsDataList.iterator();
        while (iterator.hasNext()) {
            GpsData gpsData = iterator.next();
            long elapsedMills = Duration.between(gpsData.getLastUpdate(), currenTime).toMillis();
            if (elapsedMills > INACTIVITY_THRESHOLD) {
                iterator.remove();
            }
        }
    }
}
