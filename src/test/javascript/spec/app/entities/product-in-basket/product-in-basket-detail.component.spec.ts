import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FicTests2023TestModule } from '../../../test.module';
import { ProductInBasketDetailComponent } from 'app/entities/product-in-basket/product-in-basket-detail.component';
import { ProductInBasket } from 'app/shared/model/product-in-basket.model';

describe('Component Tests', () => {
  describe('ProductInBasket Management Detail Component', () => {
    let comp: ProductInBasketDetailComponent;
    let fixture: ComponentFixture<ProductInBasketDetailComponent>;
    const route = ({ data: of({ productInBasket: new ProductInBasket(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FicTests2023TestModule],
        declarations: [ProductInBasketDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductInBasketDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductInBasketDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productInBasket on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productInBasket).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
