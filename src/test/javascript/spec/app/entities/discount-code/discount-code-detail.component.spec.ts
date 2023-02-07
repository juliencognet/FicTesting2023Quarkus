import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FicTests2023TestModule } from '../../../test.module';
import { DiscountCodeDetailComponent } from 'app/entities/discount-code/discount-code-detail.component';
import { DiscountCode } from 'app/shared/model/discount-code.model';

describe('Component Tests', () => {
  describe('DiscountCode Management Detail Component', () => {
    let comp: DiscountCodeDetailComponent;
    let fixture: ComponentFixture<DiscountCodeDetailComponent>;
    const route = ({ data: of({ discountCode: new DiscountCode(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FicTests2023TestModule],
        declarations: [DiscountCodeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DiscountCodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DiscountCodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load discountCode on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.discountCode).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
