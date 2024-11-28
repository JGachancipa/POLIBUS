import { createContext } from 'react';
import { AuthContextType } from 'context/AuthContext/AuthTypes';

export const AuthContext = createContext<AuthContextType | undefined>(undefined);
