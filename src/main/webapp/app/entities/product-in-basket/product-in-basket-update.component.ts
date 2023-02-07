import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductInBasket, ProductInBasket } from 'app/shared/model/product-in-basket.model';
import { ProductInBasketService } from './product-in-basket.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';
import { IBasket } from 'app/shared/model/basket.model';
import { BasketService } from 'app/entities/basket/basket.service';

type SelectableEntity = IProduct | IBasket;

@Component({
  selector: 'jhi-product-in-basket-update',
  templateUrl: './product-in-basket-update.component.html',
})
export class ProductInBasketUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];
  baskets: IBasket[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required]],
    productId: [],
    basketId: [],
  });

  constructor(
    protected productInBasketService: ProductInBasketService,
    protected productService: ProductService,
    protected basketService: BasketService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productInBasket }) => {
      this.updateForm(productInBasket);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.basketService.query().subscribe((res: HttpResponse<IBasket[]>) => (this.baskets = res.body || []));
    });
  }

  updateForm(productInBasket: IProductInBasket): void {
    this.editForm.patchValue({
      id: productInBasket.id,
      quantity: productInBasket.quantity,
      productId: productInBasket.productId,
      basketId: productInBasket.basketId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productInBasket = this.createFromForm();
    if (productInBasket.id !== undefined) {
      this.subscribeToSaveResponse(this.productInBasketService.update(productInBasket));
    } else {
      this.subscribeToSaveResponse(this.productInBasketService.create(productInBasket));
    }
  }

  private createFromForm(): IProductInBasket {
    return {
      ...new ProductInBasket(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      productId: this.editForm.get(['productId'])!.value,
      basketId: this.editForm.get(['basketId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductInBasket>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
