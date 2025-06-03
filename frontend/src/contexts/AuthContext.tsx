import React, { createContext, useContext, useState, useCallback } from 'react';
import api from '../services/api';

interface AuthState {
  cliente: {
    id: string;
    nome: string;
    email: string;
    perfil: string;
  } | null;
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
    perfil: string;
  } | null;
  signIn(credentials: SignInCredentials): Promise<void>;
  signOut(): void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextData>({} as AuthContextData);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [data, setData] = useState<AuthState>(() => {
    const cliente = localStorage.getItem('@CineTech:cliente');

    if (cliente) {
      return { cliente: JSON.parse(cliente) };
    }

    return { cliente: null };
  });

  const signIn = useCallback(async ({ email, senha }: SignInCredentials) => {
    try {
      const response = await api.post('/auth/login', {
        email: email.trim(),
        senha: senha.trim()
      });

      const { id, nome, email: emailCliente, perfil } = response.data;

      const cliente = { id, nome, email: emailCliente, perfil };
      localStorage.setItem('@CineTech:cliente', JSON.stringify(cliente));

      setData({ cliente });
    } catch (error) {
      console.error('Erro ao fazer login:', error);
      throw new Error('Email ou senha inválidos');
    }
  }, []);

  const signOut = useCallback(() => {
    localStorage.removeItem('@CineTech:cliente');
    setData({ cliente: null });
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