import { ICard } from "./Card";

export interface IUser {
  id: number;
  firstName: string;
  lastName: string;
  userName: string;
  emailAddress: string;
  password: string;
  wallet: number;
<<<<<<< HEAD
}

export interface IUserLoginBody {
  userName: string;
  password: string;
}

export interface IUserRegisterBody {
  firstName: string;
  lastName: string;
  emailAddress: string;
  password: string;
=======
  cards: ICard[] | null;
>>>>>>> 245f6b4baaca37506fa14ebf49878e57f5303ad6
}
