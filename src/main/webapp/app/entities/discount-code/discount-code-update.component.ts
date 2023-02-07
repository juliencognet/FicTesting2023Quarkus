import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDiscountCode, DiscountCode } from 'app/shared/model/discount-code.model';
import { DiscountCodeService } from './discount-code.service';

@Component({
  selector: 'jhi-discount-code-update',
  templateUrl: './discount-code-update.component.html',
})
export class DiscountCodeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    discount: [null, [Validators.required]],
  });

  constructor(protected discountCodeService: DiscountCodeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ discountCode }) => {
      this.updateForm(discountCode);
    });
  }

  updateForm(discountCode: IDiscountCode): void {
    this.editForm.patchValue({
      id: discountCode.id,
      code: discountCode.code,
      discount: discountCode.discount,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const discountCode = this.createFromForm();
    if (discountCode.id !== undefined) {
      this.subscribeToSaveResponse(this.discountCodeService.update(discountCode));
    } else {
      this.subscribeToSaveResponse(this.discountCodeService.create(discountCode));
    }
  }

  private createFromForm(): IDiscountCode {
    return {
      ...new DiscountCode(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      discount: this.editForm.get(['discount'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiscountCode>>): void {
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
}
