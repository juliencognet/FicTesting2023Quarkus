import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDiscountCode } from 'app/shared/model/discount-code.model';

@Component({
  selector: 'jhi-discount-code-detail',
  templateUrl: './discount-code-detail.component.html',
})
export class DiscountCodeDetailComponent implements OnInit {
  discountCode: IDiscountCode | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ discountCode }) => (this.discountCode = discountCode));
  }

  previousState(): void {
    window.history.back();
  }
}
