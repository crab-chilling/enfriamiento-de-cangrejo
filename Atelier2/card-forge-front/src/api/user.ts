import { AxiosResponse } from "axios";
import { IUser } from "@/types/User";
import axiosInstance from "./conf";

interface IUserRegisterBody {
  firstName: string | null;
  lastName: string | null;
  userName: string | null;
  emailAddress: string | null;
  password: string | null;
}

export async function register(
  user: IUserRegisterBody,
): Promise<IUser | undefined> {
  try {
    const response: AxiosResponse<IUser> = await axiosInstance.post(
      "/user/register",
      user,
    );
    return response.data;
  } catch (error) {
    console.error(error);
  }
}

export async function getUserInfo(): Promise<IUser | undefined> {
  try {
    const response: AxiosResponse<IUser> = await axiosInstance.get("/user/");
    return response.data;
  } catch (error) {
    console.error(error);
  }
}
