import React from "react";
import { CustomMarker } from "components/Map/CustomMarker";

interface MarkerProps {
    data: { id: number; latitude: number; longitude: number }[];
    map: any;
}

const Markers: React.FC<MarkerProps> = ({ data, map }) => {
    return (
        <>
            {data.map((bus) => (
                <CustomMarker
                    key={bus.id}
                    id={`${bus.id}`}
                    latitude={bus.latitude}
                    longitude={bus.longitude}
                    map={map}
                    isDefaultMarker={true}
                />
            ))}
        </>
    );
};

export default React.memo(Markers);
