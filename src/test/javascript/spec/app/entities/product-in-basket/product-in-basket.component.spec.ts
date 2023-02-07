import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FicTests2023TestModule } from '../../../test.module';
import { ProductInBasketComponent } from 'app/entities/product-in-basket/product-in-basket.component';
import { ProductInBasketService } from 'app/entities/product-in-basket/product-in-basket.service';
import { ProductInBasket } from 'app/shared/model/product-in-basket.model';

describe('Component Tests', () => {
  describe('ProductInBasket Management Component', () => {
    let comp: ProductInBasketComponent;
    let fixture: ComponentFixture<ProductInBasketComponent>;
    let service: ProductInBasketService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FicTests2023TestModule],
        declarations: [ProductInBasketComponent],
      })
        .overrideTemplate(ProductInBasketComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductInBasketComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductInBasketService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductInBasket(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productInBaskets && comp.productInBaskets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
