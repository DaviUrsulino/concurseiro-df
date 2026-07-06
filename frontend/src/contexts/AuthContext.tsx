'use client';

import React, { createContext, useContext, useState, useEffect } from 'react';

interface AuthUser {
  token: string;
  nome: string;
  email: string;
  nivelEscolaridade: string;
}

interface AuthContextData {
  user: AuthUser | null;
  login: (data: AuthUser) => void;
  logout: () => void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextData>({} as AuthContextData);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<AuthUser | null>(null);

  useEffect(() => {
    const storagedUser = localStorage.getItem('@ConcurseiroDF:user');
    const storagedToken = localStorage.getItem('@ConcurseiroDF:token');

    if (storagedUser && storagedToken) {
      setUser({ ...JSON.parse(storagedUser), token: storagedToken });
    }
  }, []);

  function login(userData: AuthUser) {
    setUser(userData);
    localStorage.setItem('@ConcurseiroDF:user', JSON.stringify({
      nome: userData.nome,
      email: userData.email,
      nivelEscolaridade: userData.nivelEscolaridade
    }));
    localStorage.setItem('@ConcurseiroDF:token', userData.token);
  }

  function logout() {
    setUser(null);
    localStorage.removeItem('@ConcurseiroDF:user');
    localStorage.removeItem('@ConcurseiroDF:token');
  }

  return (
    <AuthContext.Provider value={{ user, login, logout, isAuthenticated: !!user }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  return context;
}
