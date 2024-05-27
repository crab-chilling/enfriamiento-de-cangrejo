import { AxiosResponse } from "axios";
import axiosInstance from "./conf";

interface IUserLoginBody {
  userName: string;
  password: string;
}

export async function authenticate(
  user: IUserLoginBody,
): Promise<string | undefined> {
  try {
    const response: AxiosResponse<string> = await axiosInstance.post(
      "/login",
      user,
    );
    return response.data;
  } catch (error) {
    console.error(error);
  }
}
