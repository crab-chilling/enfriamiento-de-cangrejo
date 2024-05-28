export interface ICard {
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

export interface ICardWithOnSaleStatus extends ICard {
  isOnSale: boolean;
}
