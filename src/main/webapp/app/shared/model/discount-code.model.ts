import { IBasket } from 'app/shared/model/basket.model';

export interface IDiscountCode {
  id?: number;
  code?: string;
  discount?: number;
  baskets?: IBasket[];
}

export class DiscountCode implements IDiscountCode {
  constructor(public id?: number, public code?: string, public discount?: number, public baskets?: IBasket[]) {}
}
