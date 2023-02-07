import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductInBasket } from 'app/shared/model/product-in-basket.model';

@Component({
  selector: 'jhi-product-in-basket-detail',
  templateUrl: './product-in-basket-detail.component.html',
})
export class ProductInBasketDetailComponent implements OnInit {
  productInBasket: IProductInBasket | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productInBasket }) => (this.productInBasket = productInBasket));
  }

  previousState(): void {
    window.history.back();
  }
}
