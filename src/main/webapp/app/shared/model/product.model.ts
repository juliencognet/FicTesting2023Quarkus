export interface IProduct {
  id?: number;
  productName?: string;
  unitPrice?: number;
  ean13BarCode?: string;
  brand?: string;
  categories?: string;
  imageUrl?: string;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public productName?: string,
    public unitPrice?: number,
    public ean13BarCode?: string,
    public brand?: string,
    public categories?: string,
    public imageUrl?: string
  ) {}
}
