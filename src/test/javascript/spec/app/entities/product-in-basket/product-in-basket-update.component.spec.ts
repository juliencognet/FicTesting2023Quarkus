import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FicTests2023TestModule } from '../../../test.module';
import { ProductInBasketUpdateComponent } from 'app/entities/product-in-basket/product-in-basket-update.component';
import { ProductInBasketService } from 'app/entities/product-in-basket/product-in-basket.service';
import { ProductInBasket } from 'app/shared/model/product-in-basket.model';

describe('Component Tests', () => {
  describe('ProductInBasket Management Update Component', () => {
    let comp: ProductInBasketUpdateComponent;
    let fixture: ComponentFixture<ProductInBasketUpdateComponent>;
    let service: ProductInBasketService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FicTests2023TestModule],
        declarations: [ProductInBasketUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProductInBasketUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductInBasketUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductInBasketService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductInBasket(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductInBasket();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
