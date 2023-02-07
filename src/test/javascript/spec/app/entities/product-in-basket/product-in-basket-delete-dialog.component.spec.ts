import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FicTests2023TestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { ProductInBasketDeleteDialogComponent } from 'app/entities/product-in-basket/product-in-basket-delete-dialog.component';
import { ProductInBasketService } from 'app/entities/product-in-basket/product-in-basket.service';

describe('Component Tests', () => {
  describe('ProductInBasket Management Delete Component', () => {
    let comp: ProductInBasketDeleteDialogComponent;
    let fixture: ComponentFixture<ProductInBasketDeleteDialogComponent>;
    let service: ProductInBasketService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FicTests2023TestModule],
        declarations: [ProductInBasketDeleteDialogComponent],
      })
        .overrideTemplate(ProductInBasketDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductInBasketDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductInBasketService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
