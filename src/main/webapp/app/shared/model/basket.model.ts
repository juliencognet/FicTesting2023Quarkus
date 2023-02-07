import { Moment } from 'moment';
import { IProductInBasket } from 'app/shared/model/product-in-basket.model';
import { IDiscountCode } from 'app/shared/model/discount-code.model';

export interface IBasket {
  id?: number;
  totalPrice?: number;
  creationDate?: Moment;
  products?: IProductInBasket[];
  discountCodes?: IDiscountCode[];
}

export class Basket implements IBasket {
  constructor(
    public id?: number,
    public totalPrice?: number,
    public creationDate?: Moment,
    public products?: IProductInBasket[],
    public discountCodes?: IDiscountCode[]
  ) {}
}
