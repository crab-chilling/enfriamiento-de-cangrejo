import { AxiosResponse } from "axios";
import { IUserLoginResponse, IUserRegisterBody } from "@/types/User";
import axiosInstance from "./conf";

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
