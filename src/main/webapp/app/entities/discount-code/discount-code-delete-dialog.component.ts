import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDiscountCode } from 'app/shared/model/discount-code.model';
import { DiscountCodeService } from './discount-code.service';

@Component({
  templateUrl: './discount-code-delete-dialog.component.html',
})
export class DiscountCodeDeleteDialogComponent {
  discountCode?: IDiscountCode;

  constructor(
    protected discountCodeService: DiscountCodeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.discountCodeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('discountCodeListModification');
      this.activeModal.close();
    });
  }
}
