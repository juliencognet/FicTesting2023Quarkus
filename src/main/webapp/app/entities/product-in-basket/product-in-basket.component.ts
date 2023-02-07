import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductInBasket } from 'app/shared/model/product-in-basket.model';
import { ProductInBasketService } from './product-in-basket.service';
import { ProductInBasketDeleteDialogComponent } from './product-in-basket-delete-dialog.component';

@Component({
  selector: 'jhi-product-in-basket',
  templateUrl: './product-in-basket.component.html',
})
export class ProductInBasketComponent implements OnInit, OnDestroy {
  productInBaskets?: IProductInBasket[];
  eventSubscriber?: Subscription;

  constructor(
    protected productInBasketService: ProductInBasketService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.productInBasketService.query().subscribe((res: HttpResponse<IProductInBasket[]>) => (this.productInBaskets = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProductInBaskets();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProductInBasket): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductInBaskets(): void {
    this.eventSubscriber = this.eventManager.subscribe('productInBasketListModification', () => this.loadAll());
  }

  delete(productInBasket: IProductInBasket): void {
    const modalRef = this.modalService.open(ProductInBasketDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productInBasket = productInBasket;
  }
}
