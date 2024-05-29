import { AxiosResponse } from "axios";
import axiosInstance from "./conf";
import { IRoom } from "@/types/Room";

export async function getGameRooms(): Promise<IRoom[]> {
  try {
    const response: AxiosResponse<IRoom[]> = await axiosInstance.get("/rooms");
    return response.data;
  } catch (error) {
    console.error("Error fetching game rooms:", error);
    return [];
  }
}

export async function createGameRoom(
  room: Partial<IRoom>,
): Promise<IRoom | undefined> {
  try {
    const response: AxiosResponse<IRoom> = await axiosInstance.post(
      "/rooms",
      room,
    );
    return response.data;
  } catch (error) {
    console.error("Error creating game room:", error);
  }
}

export async function joinGameRoom(
  roomId: number,
  userId: number,
): Promise<IRoom | undefined> {
  try {
    const response: AxiosResponse<IRoom> = await axiosInstance.post(
      "/rooms/join" + roomId,
      { userId },
    );
    return response.data;
  } catch (error) {
    console.error("Error joining game room:", error);
  }
}
