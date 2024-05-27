"use client";

import React, {
  createContext,
  useContext,
  useState,
  ReactNode,
  useEffect,
} from "react";
import { IJwtPayload } from "@/types/Jwt";
import { IUser } from "@/types/User";
import Cookies from "js-cookie";
import { parseJwt } from "@/utils/jwt";
import { getUserInfo } from "@/api/user";

interface AuthContextType {
  isLoggedIn: boolean;
  userContext: IUser | null;
  login: (token: string) => void;
  logout: () => void;
  refreshUserContext: () => Promise<void>;
}

export const AuthContext = createContext<AuthContextType | undefined>(
  undefined,
);

export const AuthProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [tokenPayload, setTokenPayload] = useState<IJwtPayload | null>(null);
  const [userContext, setUserContext] = useState<IUser | null>(null);

  const isTokenExpired = (tokenPayload: IJwtPayload | null): boolean => {
    if (!tokenPayload || !tokenPayload.exp) return true;
    return tokenPayload.exp * 1000 < Date.now();
  };

  const refreshUserContext = async () => {
    try {
      const user = await getUserInfo();
      if (user) {
        setUserContext(user);
      }
    } catch (error) {
      console.error("Failed to update user context:", error);
    }
  };

  const login = async (token: string) => {
    setTokenPayload(parseJwt(token));
    Cookies.set("token", token);
    await refreshUserContext();
    setIsLoggedIn(true);
  };

  const logout = () => {
    setTokenPayload(null);
    Cookies.remove("token");
    setUserContext(null);
    setIsLoggedIn(false);
  };

  useEffect(() => {
    const token = Cookies.get("token");
    if (token) {
      const decodedToken = parseJwt(token);
      if (!isTokenExpired(decodedToken)) {
        login(token);
      } else {
        logout();
      }
    }
  }, []);

  return (
    <AuthContext.Provider
      value={{ isLoggedIn, userContext, login, logout, refreshUserContext }}
    >
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
