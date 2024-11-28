import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import React, { useEffect, useRef, useState } from 'react';

interface MapProps {
    coord: [number, number];
    children: (map: L.Map) => React.ReactNode;
}

export const Map: React.FC<MapProps> = ({ children, coord }) => {
    const mapRef = useRef<L.Map | null>(null);
    const [isMapReady, setIsMapReady] = useState(false);

    useEffect(() => {
        if (!mapRef.current && coord) {
            mapRef.current = L.map('map', { 
                zoom: 20, 
                center: coord, 
                zoomControl: false, 
                maxBounds: [[-85, -180], [85, 180]], 
                maxBoundsViscosity: 1.0, 
            });
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 19, minZoom: 2, }).addTo(mapRef.current);
            setIsMapReady(true);
        }

        if (mapRef.current && coord) {
            mapRef.current.setView(coord, mapRef.current.getZoom());
        }

        return () => { };
    }, [coord]);

    return (
        <div id="map" style={{ width: '100%', height: '100vh' }}>
            {isMapReady && mapRef.current && children(mapRef.current)}
        </div>
    );
};
