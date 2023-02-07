import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBasket } from 'app/shared/model/basket.model';

type EntityResponseType = HttpResponse<IBasket>;
type EntityArrayResponseType = HttpResponse<IBasket[]>;

@Injectable({ providedIn: 'root' })
export class BasketService {
  public resourceUrl = SERVER_API_URL + 'api/baskets';

  constructor(protected http: HttpClient) {}

  create(basket: IBasket): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(basket);
    return this.http
      .post<IBasket>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(basket: IBasket): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(basket);
    return this.http
      .put<IBasket>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  addDiscountCode(id: number, discountCode: string): Observable<EntityResponseType> {
    return this.http
      .put<IBasket>(`${this.resourceUrl}/${id}/discount/${discountCode}`, {}, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBasket>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBasket[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(basket: IBasket): IBasket {
    const copy: IBasket = Object.assign({}, basket, {
      creationDate: basket.creationDate && basket.creationDate.isValid() ? basket.creationDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate ? moment(res.body.creationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((basket: IBasket) => {
        basket.creationDate = basket.creationDate ? moment(basket.creationDate) : undefined;
      });
    }
    return res;
  }
}
