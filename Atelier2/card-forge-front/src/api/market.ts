import { AxiosResponse } from "axios";
import axiosInstance from "./conf";
import { IMarketCard, IUserCard } from "@/types/Card";

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
  price: number,
  card: IUserCard,
): Promise<IMarketCard | undefined> {
  try {
    const response: AxiosResponse<IMarketCard> = await axiosInstance.post(
      "/store-item/sell/" + price,
      card,
    );
    return response.data;
  } catch (error) {
    console.error(error);
  }
}
