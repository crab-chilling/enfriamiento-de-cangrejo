import axios, { AxiosError } from "axios";
import Router from "next/router";
import Cookies from "js-cookie";

const axiosInstance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL,
});

axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error: AxiosError) => {
    if (error.response && error.response.status === 403) {
      Cookies.remove("token");
      Router.push("/login");
    }
    return Promise.reject(error);
  },
);

export default axiosInstance;
