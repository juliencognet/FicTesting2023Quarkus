import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductInBasket } from 'app/shared/model/product-in-basket.model';
import { ProductInBasketService } from './product-in-basket.service';

@Component({
  templateUrl: './product-in-basket-delete-dialog.component.html',
})
export class ProductInBasketDeleteDialogComponent {
  productInBasket?: IProductInBasket;

  constructor(
    protected productInBasketService: ProductInBasketService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productInBasketService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productInBasketListModification');
      this.activeModal.close();
    });
  }
}
