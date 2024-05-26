"use client";

import React, { createContext, useContext, useState, ReactNode } from "react";
import { IUser } from "@/types/Card";

// Mettre à jour le type du contexte pour inclure les informations sur l'utilisateur
interface AuthContextType {
  isLoggedIn: boolean;
  user: IUser | null; // Utiliser null si aucun utilisateur n'est connecté
  login: (user: IUser) => void;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextType | undefined>(
  undefined,
);

export const AuthProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState<IUser | null>(null);

  const login = (user: IUser) => {
    setIsLoggedIn(true);
    setUser(user);
  };

  const logout = () => {
    setIsLoggedIn(false);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ isLoggedIn, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};
