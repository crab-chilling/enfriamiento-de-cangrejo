import axios, { AxiosError } from "axios";
import Router from "next/router";
import Cookies from "js-cookie";

const axiosInstance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL,
});

axiosInstance.interceptors.request.use(
  (config) => {
    config.headers["Accept"] = "application/json";
    const token: string | undefined = Cookies.get("token");
    if (token) config.headers["Authorization"] = token;
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error: AxiosError) => {
    if (error.response && error.response.status === 401) {
      Cookies.remove("token");
      Router.push("/login");
    }
    return Promise.reject(error);
  },
);

export default axiosInstance;
