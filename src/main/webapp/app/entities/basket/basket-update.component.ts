import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBasket, Basket } from 'app/shared/model/basket.model';
import { BasketService } from './basket.service';
import { IDiscountCode } from 'app/shared/model/discount-code.model';
import { DiscountCodeService } from 'app/entities/discount-code/discount-code.service';

@Component({
  selector: 'jhi-basket-update',
  templateUrl: './basket-update.component.html',
})
export class BasketUpdateComponent implements OnInit {
  isSaving = false;
  discountcodes: IDiscountCode[] = [];
  creationDateDp: any;

  editForm = this.fb.group({
    id: [],
    totalPrice: [null, [Validators.required]],
    creationDate: [null, [Validators.required]],
    discountCodes: [],
  });

  constructor(
    protected basketService: BasketService,
    protected discountCodeService: DiscountCodeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ basket }) => {
      this.updateForm(basket);

      this.discountCodeService.query().subscribe((res: HttpResponse<IDiscountCode[]>) => (this.discountcodes = res.body || []));
    });
  }

  updateForm(basket: IBasket): void {
    this.editForm.patchValue({
      id: basket.id,
      totalPrice: basket.totalPrice,
      creationDate: basket.creationDate,
      discountCodes: basket.discountCodes,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const basket = this.createFromForm();
    if (basket.id !== undefined) {
      this.subscribeToSaveResponse(this.basketService.update(basket));
    } else {
      this.subscribeToSaveResponse(this.basketService.create(basket));
    }
  }

  private createFromForm(): IBasket {
    return {
      ...new Basket(),
      id: this.editForm.get(['id'])!.value,
      totalPrice: this.editForm.get(['totalPrice'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value,
      discountCodes: this.editForm.get(['discountCodes'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBasket>>): void {
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

  trackById(index: number, item: IDiscountCode): any {
    return item.id;
  }

  getSelected(selectedVals: IDiscountCode[], option: IDiscountCode): IDiscountCode {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
