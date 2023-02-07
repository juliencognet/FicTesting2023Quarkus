import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductInBasket } from 'app/shared/model/product-in-basket.model';

type EntityResponseType = HttpResponse<IProductInBasket>;
type EntityArrayResponseType = HttpResponse<IProductInBasket[]>;

@Injectable({ providedIn: 'root' })
export class ProductInBasketService {
  public resourceUrl = SERVER_API_URL + 'api/product-in-baskets';

  constructor(protected http: HttpClient) {}

  create(productInBasket: IProductInBasket): Observable<EntityResponseType> {
    return this.http.post<IProductInBasket>(this.resourceUrl, productInBasket, { observe: 'response' });
  }

  update(productInBasket: IProductInBasket): Observable<EntityResponseType> {
    return this.http.put<IProductInBasket>(this.resourceUrl, productInBasket, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductInBasket>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductInBasket[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
