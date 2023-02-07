import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { Basket } from 'app/shared/model/basket.model';

@Component({
  selector: 'jhi-basket-icon',
  templateUrl: './basket-icon.component.html',
  styleUrls: ['./basket-icon.component.scss'],
})
export class BasketIconComponent implements OnInit, OnChanges {
  @Input() basket: Basket = {};
  numberOfProducts = 0;

  constructor() {}

  ngOnInit(): void {
    this.computeNumberOfElements();
  }

  ngOnChanges(changes: import('@angular/core').SimpleChanges): void {
    console.log(changes);
    this.computeNumberOfElements();
  }

  computeNumberOfElements(): void {
    if (this.basket && this.basket.products) {
      this.numberOfProducts = this.basket.products.length;
    }
  }
}
