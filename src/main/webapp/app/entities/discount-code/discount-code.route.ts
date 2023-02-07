import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDiscountCode, DiscountCode } from 'app/shared/model/discount-code.model';
import { DiscountCodeService } from './discount-code.service';
import { DiscountCodeComponent } from './discount-code.component';
import { DiscountCodeDetailComponent } from './discount-code-detail.component';
import { DiscountCodeUpdateComponent } from './discount-code-update.component';

@Injectable({ providedIn: 'root' })
export class DiscountCodeResolve implements Resolve<IDiscountCode> {
  constructor(private service: DiscountCodeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDiscountCode> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((discountCode: HttpResponse<DiscountCode>) => {
          if (discountCode.body) {
            return of(discountCode.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DiscountCode());
  }
}

export const discountCodeRoute: Routes = [
  {
    path: '',
    component: DiscountCodeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DiscountCodes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DiscountCodeDetailComponent,
    resolve: {
      discountCode: DiscountCodeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DiscountCodes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DiscountCodeUpdateComponent,
    resolve: {
      discountCode: DiscountCodeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DiscountCodes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DiscountCodeUpdateComponent,
    resolve: {
      discountCode: DiscountCodeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DiscountCodes',
    },
    canActivate: [UserRouteAccessService],
  },
];
