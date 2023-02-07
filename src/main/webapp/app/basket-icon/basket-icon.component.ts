import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { Basket } from 'app/shared/model/basket.model';
import { faShoppingCart } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'jhi-basket-icon',
  templateUrl: './basket-icon.component.html',
  styleUrls: ['./basket-icon.component.scss'],
})
export class BasketIconComponent implements OnInit, OnChanges {
  @Input() basket: Basket = {};
  numberOfProducts = 0;
  faShoppingCart = faShoppingCart;

  constructor() {}

  ngOnInit(): void {
    this.computeNumberOfElements();
  }

  ngOnChanges(): void {
    this.computeNumberOfElements();
  }

  computeNumberOfElements(): void {
    if (this.basket && this.basket.products) {
      this.numberOfProducts = this.basket.products.length;
    }
  }
}
