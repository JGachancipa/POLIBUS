import React, { useState, useEffect } from "react";
import { useFetchBusData } from "hooks/useFetchBusData";
import { Map } from "components/Map/Map";
import Markers from "components/Map/Markers";

const MapContainer = () => {
    const { data, error, loading } = useFetchBusData();
    const [initialCoordinates, setInitialCoordinates] = useState<[number, number] | null>(null);

    useEffect(() => {
        if (loading) return;
        if (data.length > 0 && !initialCoordinates) {
            const { latitude, longitude } = data[0];
            setInitialCoordinates([latitude, longitude]);
        } else if (!initialCoordinates) {
            setInitialCoordinates([4.6454, -74.06197]);
        }
    }, [data, initialCoordinates, loading]);

    if (error) return <div>Error: {error}</div>;
    if (!initialCoordinates) return <div>Cargando mapa...</div>;

    return (
        <div className="d-flex" style={{ height: "100vh" }}>
            <div className="flex-fill position-relative">
                <Map coord={initialCoordinates}>
                    {(map) => <Markers data={data} map={map} />}
                </Map>
            </div>
        </div>
    );
};

export default React.memo(MapContainer);
