import React, { useState, useEffect, ReactNode } from 'react';
import { AuthContext } from 'context/AuthContext/AuthContext';

interface AuthProviderProps {
    children: ReactNode;
}

const credentials = {
    username: 'root',
    password: 'bG96M0FtN2p1czFDemZ1',
};

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
    const [authToken, setAuthToken] = useState<string | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchAuthToken = async () => {
            try {
                const response = await fetch('https://polibug-gps-dcef309899c5.herokuapp.com/api/auth/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(credentials),
                    credentials: 'include'
                });

                if (!response.ok) {
                    throw new Error(`Error de autenticación: ${response.statusText}`);
                }

                const token = await response.text();
                if (!token) {
                    throw new Error('No se recibió un token de autenticación válido.');
                }

                setAuthToken(token);
            } catch (err) {
                setError(err instanceof Error ? err.message : 'Error desconocido');
            }
        };

        fetchAuthToken();
    }, []);

    return (
        <AuthContext.Provider value={{ authToken, error }}>
            {children}
        </AuthContext.Provider>
    );
};
