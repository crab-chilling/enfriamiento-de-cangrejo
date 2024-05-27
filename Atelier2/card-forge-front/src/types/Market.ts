import { ICard } from "./Card";
import { IUser } from "./User";

export interface IMarketCard {
  id: number;
  card: ICard;
  user: IUser;
  price: number;
}
