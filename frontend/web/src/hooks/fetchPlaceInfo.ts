export const fetchPlaceInfo = async (lat: number, lng: number) => {
    try {
        const resp = await fetch( `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}`);
        if(!resp.ok) throw new Error("Error al obtener la informacion del lugar");
        const data = await resp.json();
        return {name: data.display_name || 'Lugar Desconocido', address: data.address || 'Direccion no disponible para este punto geografico'}
    } catch (error) {
        console.log(error);
        throw error;
    }
};
