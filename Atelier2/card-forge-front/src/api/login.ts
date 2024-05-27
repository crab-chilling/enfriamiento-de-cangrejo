import { AxiosResponse } from "axios";
import { IUserLoginResponse, IUserLoginBody } from "@/types/User";
import axiosInstance from "./conf";

export async function authenticate(
  user: IUserLoginBody,
): Promise<IUserLoginResponse | undefined> {
  try {
    const response: AxiosResponse<IUserLoginResponse> =
      await axiosInstance.post("/login", user);
    return response.data;
  } catch (error) {
    console.error(error);
  }
}
