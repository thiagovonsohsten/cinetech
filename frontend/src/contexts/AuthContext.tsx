import React, { createContext, useContext, useState, useCallback } from 'react';
import api from '../services/api';

interface AuthState {
  token: string;
  cliente: {
    id: string;
    nome: string;
    email: string;
  };
}

interface SignInCredentials {
  email: string;
  senha: string;
}

interface AuthContextData {
  cliente: {
    id: string;
    nome: string;
    email: string;
  } | null;
  signIn(credentials: SignInCredentials): Promise<void>;
  signOut(): void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextData>({} as AuthContextData);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [data, setData] = useState<AuthState>(() => {
    const token = localStorage.getItem('@CineTech:token');
    const cliente = localStorage.getItem('@CineTech:cliente');

    if (token && cliente) {
      api.defaults.headers.authorization = `Bearer ${token}`;
      return { token, cliente: JSON.parse(cliente) };
    }

    return {} as AuthState;
  });

  const signIn = useCallback(async ({ email, senha }: SignInCredentials) => {
    const response = await api.post('/auth/login', { email, senha });
    const { token, cliente } = response.data;

    localStorage.setItem('@CineTech:token', token);
    localStorage.setItem('@CineTech:cliente', JSON.stringify(cliente));

    api.defaults.headers.authorization = `Bearer ${token}`;

    setData({ token, cliente });
  }, []);

  const signOut = useCallback(() => {
    localStorage.removeItem('@CineTech:token');
    localStorage.removeItem('@CineTech:cliente');

    setData({} as AuthState);
  }, []);

  return (
    <AuthContext.Provider
      value={{
        cliente: data.cliente,
        signIn,
        signOut,
        isAuthenticated: !!data.cliente,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export function useAuth(): AuthContextData {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }

  return context;
} 