import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDiscountCode } from 'app/shared/model/discount-code.model';

type EntityResponseType = HttpResponse<IDiscountCode>;
type EntityArrayResponseType = HttpResponse<IDiscountCode[]>;

@Injectable({ providedIn: 'root' })
export class DiscountCodeService {
  public resourceUrl = SERVER_API_URL + 'api/discount-codes';

  constructor(protected http: HttpClient) {}

  create(discountCode: IDiscountCode): Observable<EntityResponseType> {
    return this.http.post<IDiscountCode>(this.resourceUrl, discountCode, { observe: 'response' });
  }

  update(discountCode: IDiscountCode): Observable<EntityResponseType> {
    return this.http.put<IDiscountCode>(this.resourceUrl, discountCode, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDiscountCode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDiscountCode[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
