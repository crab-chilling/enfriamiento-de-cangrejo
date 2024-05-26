export interface IUser {
  id: number;
  firstName: string;
  lastName: string;
  userName: string;
  emailAddress: string;
  password: string;
  wallet: number;
  cards: IMarketCard[];
}

export interface IUserCard {
  id: number;
  name: string;
  description: string;
  family: string;
  affinity: string;
  imageUrl: string;
  miniatureUrl: string;
  energy: number;
  health: number;
  attack: number;
  defence: number;
}

export interface IMarketCard extends IUserCard {
  price: number;
}
