import { AxiosResponse } from "axios";
import {
  IUserLoginBody,
  IUserLoginResponse,
  IUserRegisterBody,
} from "@/types/User";
import axiosInstance from "./conf";

export async function authenticate(
  user: IUserLoginBody,
): Promise<IUserLoginResponse | undefined> {
  try {
    const response: AxiosResponse<IUserLoginResponse> =
      await axiosInstance.post("/user/login", user);
    return response.data;
  } catch (error) {
    console.error(error);
  }
}

export async function register(
  user: IUserRegisterBody,
): Promise<IUserLoginResponse | undefined> {
  try {
    const response: AxiosResponse<IUserLoginResponse> =
      await axiosInstance.post("/user/register", user);
    return response.data;
  } catch (error) {
    console.error(error);
  }
}
