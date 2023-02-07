import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FicTests2023TestModule } from '../../../test.module';
import { DiscountCodeComponent } from 'app/entities/discount-code/discount-code.component';
import { DiscountCodeService } from 'app/entities/discount-code/discount-code.service';
import { DiscountCode } from 'app/shared/model/discount-code.model';

describe('Component Tests', () => {
  describe('DiscountCode Management Component', () => {
    let comp: DiscountCodeComponent;
    let fixture: ComponentFixture<DiscountCodeComponent>;
    let service: DiscountCodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FicTests2023TestModule],
        declarations: [DiscountCodeComponent],
      })
        .overrideTemplate(DiscountCodeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DiscountCodeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DiscountCodeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DiscountCode(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.discountCodes && comp.discountCodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
