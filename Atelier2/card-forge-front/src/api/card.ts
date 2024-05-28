import { AxiosResponse } from "axios";
import axiosInstance from "./conf";
import { ICard } from "@/types/Card";

export async function getUserCardCollection(): Promise<ICard[]> {
  try {
    const response: AxiosResponse<ICard[]> =
      await axiosInstance.get("/user/cards");
    return response.data;
  } catch (error) {
    console.error("Error fetching user card collection:", error);
    return [];
  }
}
