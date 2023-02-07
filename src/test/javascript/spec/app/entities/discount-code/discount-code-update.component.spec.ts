import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FicTests2023TestModule } from '../../../test.module';
import { DiscountCodeUpdateComponent } from 'app/entities/discount-code/discount-code-update.component';
import { DiscountCodeService } from 'app/entities/discount-code/discount-code.service';
import { DiscountCode } from 'app/shared/model/discount-code.model';

describe('Component Tests', () => {
  describe('DiscountCode Management Update Component', () => {
    let comp: DiscountCodeUpdateComponent;
    let fixture: ComponentFixture<DiscountCodeUpdateComponent>;
    let service: DiscountCodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FicTests2023TestModule],
        declarations: [DiscountCodeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DiscountCodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DiscountCodeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DiscountCodeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DiscountCode(123);
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
        const entity = new DiscountCode();
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
