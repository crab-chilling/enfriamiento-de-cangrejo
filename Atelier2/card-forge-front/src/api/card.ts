import { AxiosResponse } from "axios";
import axiosInstance from "./conf";
import { IUser } from "@/types/Card";

export async function getUserCardCollection(
  userId: number,
): Promise<IUser | undefined> {
  try {
    const response: AxiosResponse<IUser> = await axiosInstance.get(
      "/user/" + userId,
    );
    return response.data;
  } catch (error) {
    console.error(error);
  }
}
