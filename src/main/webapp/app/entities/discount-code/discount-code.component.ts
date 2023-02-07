import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDiscountCode } from 'app/shared/model/discount-code.model';
import { DiscountCodeService } from './discount-code.service';
import { DiscountCodeDeleteDialogComponent } from './discount-code-delete-dialog.component';

@Component({
  selector: 'jhi-discount-code',
  templateUrl: './discount-code.component.html',
})
export class DiscountCodeComponent implements OnInit, OnDestroy {
  discountCodes?: IDiscountCode[];
  eventSubscriber?: Subscription;

  constructor(
    protected discountCodeService: DiscountCodeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.discountCodeService.query().subscribe((res: HttpResponse<IDiscountCode[]>) => (this.discountCodes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDiscountCodes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDiscountCode): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDiscountCodes(): void {
    this.eventSubscriber = this.eventManager.subscribe('discountCodeListModification', () => this.loadAll());
  }

  delete(discountCode: IDiscountCode): void {
    const modalRef = this.modalService.open(DiscountCodeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.discountCode = discountCode;
  }
}
