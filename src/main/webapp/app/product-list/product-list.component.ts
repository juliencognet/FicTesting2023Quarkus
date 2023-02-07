import { Component, OnInit } from '@angular/core';
import { ProductService } from 'app/entities/product/product.service';
import { JhiAlert, JhiAlertService } from 'ng-jhipster';
import { HttpResponse } from '@angular/common/http';
import { IBasket } from 'app/shared/model/basket.model';
import { Basket } from 'app/shared/model/basket.model';
import { ProductInBasket } from 'app/shared/model/product-in-basket.model';
import { ProductInBasketService } from 'app/entities/product-in-basket/product-in-basket.service';
import { BasketService } from 'app/entities/basket/basket.service';
import * as moment from 'moment';
import { IProduct } from 'app/shared/model/product.model';
import { faStore, faCartPlus } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'jhi-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
})
export class ProductListComponent implements OnInit {
  alerts: JhiAlert[] = [];
  products?: IProduct[];
  loading = true;
  currentBasket: any;
  basketCreated = false;
  faStore = faStore;
  faCartPlus = faCartPlus;

  constructor(
    private productService: ProductService,
    private alertService: JhiAlertService,
    private productInBasketService: ProductInBasketService,
    private basketService: BasketService
  ) {}

  ngOnInit(): void {
    this.loadProducts();
    this.retrieveBasketOrCreateIt();
  }
  loadProducts(): void {
    this.loading = true;
    this.productService.query({ size: 1000 }).subscribe(
      (res: HttpResponse<IProduct[]>) => {
        this.products = res.body ? res.body : [];
        this.loading = false;
      },
      err => console.error('Unexpected error while loading products : ' + err)
    );
  }
  selectProduct(product: IProduct): void {
    this.addProductToBasket(product);
  }

  retrieveBasketOrCreateIt(): void {
    this.basketService.query().subscribe((res: HttpResponse<IBasket[]>) => {
      const listOfBaskets = res.body ? res.body : [];
      if (listOfBaskets.length === 0) {
        this.createBasket();
      } else {
        this.currentBasket = listOfBaskets[0];
        this.refreshBasket();
      }
    });
  }

  createBasket(): void {
    const basket: Basket = new Basket();
    basket.totalPrice = 0;
    basket.creationDate = moment();
    this.basketService.create(basket).subscribe((res: HttpResponse<IBasket>) => {
      this.currentBasket = res.body ? res.body : {};
    });
  }

  refreshBasket(notify = false): void {
    this.basketService.find(this.currentBasket.id).subscribe((res: HttpResponse<IBasket>) => {
      this.currentBasket = res.body ? res.body : {};
      if (notify) {
        this.alertService.success('Nouveau produit ajouté au panier', null);
      }
    });
  }

  addProductToBasket(product: IProduct): void {
    const productInBasket = new ProductInBasket();
    productInBasket.basketId = this.currentBasket.id;
    productInBasket.productId = product.id;
    productInBasket.quantity = 1;
    this.productInBasketService.create(productInBasket).subscribe(() => {
      this.refreshBasket(true);
    });
  }
}
