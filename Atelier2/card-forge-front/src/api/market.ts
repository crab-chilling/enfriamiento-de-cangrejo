import { AxiosResponse } from "axios";
import axiosInstance from "./conf";
import { IMarketCard } from "@/types/Market";

export async function getMarketCardCollection(): Promise<
  IMarketCard[] | undefined
> {
  try {
    const response: AxiosResponse<IMarketCard[]> =
      await axiosInstance.get("/store-item/all");
    return response.data;
  } catch (error) {
    console.error(error);
  }
}

export async function getSellerMarketCardCollection(
  sellerId: number,
): Promise<IMarketCard[] | undefined> {
  try {
    const response: AxiosResponse<IMarketCard[]> = await axiosInstance.get(
      "/store-item/seller/" + sellerId,
    );
    return response.data;
  } catch (error) {
    console.error(error);
  }
}

export async function purchase(
  cardId: number,
): Promise<IMarketCard | undefined> {
  try {
    const response: AxiosResponse<IMarketCard> = await axiosInstance.post(
      "/store-item/purchase/" + cardId,
    );
    return response.data;
  } catch (error) {
    console.error(error);
  }
}

export async function sell(
  card: Partial<IMarketCard>,
): Promise<IMarketCard | undefined> {
  try {
    const response: AxiosResponse<IMarketCard> = await axiosInstance.post(
      "/store-item/sell",
      card,
    );
    return response.data;
  } catch (error) {
    console.error(error);
  }
}
