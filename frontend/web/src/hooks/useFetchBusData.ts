import { useState, useEffect, useContext } from "react";
import { AuthContext } from "context/AuthContext/AuthContext";

interface BusData {
  id: number;
  latitude: number;
  longitude: number;
}

export const useFetchBusData = () => {
  const { authToken, error: authError } = useContext(AuthContext)!;
  const [data, setData] = useState<BusData[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (authError) {
      setError(authError);
      setLoading(false);
    }
  }, [authError]);

  useEffect(() => {
    let intervalId: NodeJS.Timeout;

    const fetchBusData = async () => {
      if (!authToken) {
        setError("No se ha recibido un token de autenticación");
        setLoading(false);
        return;
      }

      try {
        setLoading(true);
        const response = await fetch("https://polibus-gps-7c20a623a9b4.herokuapp.com/api/v1/gps/all", {
          headers: { Authorization: `Bearer ${authToken}` },
        });

        if (!response.ok) {
          throw new Error(
            `Error al obtener datos de buses: ${response.statusText}`
          );
        }

        const busData = await response.json();
        setData(busData);
      } catch (err) {
        setError(err instanceof Error ? err.message : "Error desconocido");
      } finally {
        setLoading(false);
      }
    };

    if (authToken) {
      fetchBusData();
      intervalId = setInterval(fetchBusData, 5000)
    }

    return () => clearInterval(intervalId);
  }, [authToken]);

  return { data, loading, error };
};
