import L from 'leaflet';
import React, { useEffect, useMemo } from 'react';
import ReactDOMServer from 'react-dom/server';
import { TablerBus } from 'assets/TablerBus';

interface CustomMarkerProps {
    map: L.Map;
    id: string;
    latitude: number;
    longitude: number;
    isDefaultMarker?: boolean;
}

export const CustomMarker: React.FC<CustomMarkerProps> = ({
    id,
    latitude,
    longitude,
    map,
    isDefaultMarker = false,
}) => {
    const iconHtml = useMemo(
        () => ReactDOMServer.renderToStaticMarkup(
            <TablerBus style={{ position: 'absolute', width: '24px', height: '24px', color: 'red' }} />
        ),
        []
    );

    useEffect(() => {
        let existingMarker: L.Marker | undefined;

        map.eachLayer((layer) => {
            if (layer instanceof L.Marker && layer.options.icon && layer.options.icon.options.className === `custom-icon-bus-${id}`) {
                existingMarker = layer as L.Marker;
            }
        });

        if (existingMarker) {
            const startLatLng = existingMarker.getLatLng();
            const endLatLng = L.latLng(latitude, longitude);
            const steps = 100;
            const intervalTime = 20;
            let currentStep = 0;
            const interval = setInterval(() => {
                currentStep += 1;
                const latLng = L.latLng(
                    startLatLng.lat + (endLatLng.lat - startLatLng.lat) * (currentStep / steps),
                    startLatLng.lng + (endLatLng.lng - startLatLng.lng) * (currentStep / steps)
                );

                existingMarker?.setLatLng(latLng);

                if (currentStep >= steps) {
                    clearInterval(interval);
                }
            }, intervalTime);
        } else {
            const icon = isDefaultMarker
                ? L.divIcon({
                    html: iconHtml as string,
                    iconSize: [40, 40],
                    iconAnchor: [20, 40],
                    className: `custom-icon-bus-${id}`,
                })
                : L.icon({
                    iconUrl: '/default-marker.png',
                    iconSize: [25, 41],
                    iconAnchor: [12, 41],
                });

            const marker = L.marker([latitude, longitude], { icon }).addTo(map);

            const startLatLng = marker.getLatLng();
            const endLatLng = L.latLng(latitude, longitude);
            const steps = 100;
            const intervalTime = 20;

            let currentStep = 0;

            const interval = setInterval(() => {
                currentStep += 1;
                const latLng = L.latLng(
                    startLatLng.lat + (endLatLng.lat - startLatLng.lat) * (currentStep / steps),
                    startLatLng.lng + (endLatLng.lng - startLatLng.lng) * (currentStep / steps)
                );

                marker.setLatLng(latLng);

                if (currentStep >= steps) {
                    clearInterval(interval);
                }
            }, intervalTime);
        }

    }, [latitude, longitude, map, iconHtml, isDefaultMarker, id]);

    return null;
};
