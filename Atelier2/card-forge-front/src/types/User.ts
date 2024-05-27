import { ICard } from "./Card";

export interface IUser {
  id: number;
  firstName: string;
  lastName: string;
  userName: string;
  emailAddress: string;
  password: string;
  wallet: number;
  cards: ICard[] | null;
}
