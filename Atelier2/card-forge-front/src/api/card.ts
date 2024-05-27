import { AxiosResponse } from "axios";
import axiosInstance from "./conf";
import { IUser } from "@/types/User";
import { ICard } from "@/types/Card";

export async function getUserCardCollection(): Promise<ICard[]> {
  try {
    const response: AxiosResponse<IUser> = await axiosInstance.get("/user/");
    const data: IUser = response.data;
    return data.cards || [];
  } catch (error) {
    console.error("Error fetching user card collection:", error);
    return [];
  }
}
