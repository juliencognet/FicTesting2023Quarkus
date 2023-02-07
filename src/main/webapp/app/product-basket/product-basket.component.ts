import { IProductInBasket } from './../shared/model/product-in-basket.model';
import { ProductInBasketService } from './../entities/product-in-basket/product-in-basket.service';
import { Component, OnInit } from '@angular/core';
import { IBasket } from 'app/shared/model/basket.model';
import { BasketService } from 'app/entities/basket/basket.service';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService, JhiAlert } from 'ng-jhipster';
import {
  faShoppingCart,
  faStore,
  faCashRegister,
  faPlusSquare,
  faTags,
  faPlusCircle,
  faMinusCircle,
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'jhi-product-basket',
  templateUrl: './product-basket.component.html',
  styleUrls: ['./product-basket.component.scss'],
})
export class ProductBasketComponent implements OnInit {
  currentBasket: IBasket = {};
  customerId = 0;
  loading = false;
  alerts: JhiAlert[] = [];
  discountCode = '';
  faShoppingCart = faShoppingCart;
  faStore = faStore;
  faCashRegister = faCashRegister;
  faPlusSquare = faPlusSquare;
  faTags = faTags;
  faPlusCircle = faPlusCircle;
  faMinusCircle = faMinusCircle;

  constructor(
    private basketService: BasketService,
    private route: ActivatedRoute,
    private router: Router,
    private alertService: JhiAlertService,
    private productInBasketService: ProductInBasketService
  ) {}

  ngOnInit(): void {
    const basketId = Number(this.route.snapshot.paramMap.get('basketId'));
    this.loadBasket(basketId);
  }

  loadBasket(basketId: number): void {
    this.loading = true;
    this.basketService.find(basketId).subscribe((res: HttpResponse<IBasket>) => {
      this.currentBasket = res.body ? res.body : {};
      this.loading = false;
    });
  }
  backToSearch(): void {
    this.router.navigate(['/productList']);
  }

  removeProduct(productInBasket: any): void {
    if (productInBasket != null) {
      this.productInBasketService
        .delete(productInBasket.id)
        .subscribe(() => this.loadBasket(this.currentBasket.id ? this.currentBasket.id : 0));
    }
  }

  addDiscountCode(): void {
    this.loading = true;
    const basketId = this.currentBasket.id ? this.currentBasket.id : 0;
    this.basketService.addDiscountCode(basketId, this.discountCode).subscribe(
      () => {
        this.discountCode = '';
        this.loadBasket(basketId);
      },
      () => {
        this.alertService.error('Code de réduction invalide', null);
        this.loading = false;
      }
    );
  }

  updateLineAndReload(line: IProductInBasket): void {
    const currentBasketId = this.currentBasket.id ? this.currentBasket.id : 0;
    line.basketId = currentBasketId;
    if (line.product !== undefined) {
      line.productId = line.product.id;
    }
    this.loading = true;
    this.productInBasketService.update(line).subscribe(() => {
      this.loadBasket(currentBasketId);
    });
  }
  removeQuantity(line: IProductInBasket): void {
    if (line.quantity !== undefined && line.quantity > 1) {
      line.quantity = line.quantity - 1;
      this.updateLineAndReload(line);
    }
  }
  addQuantity(line: IProductInBasket): void {
    if (line.quantity !== undefined) {
      line.quantity = line.quantity + 1;
      this.updateLineAndReload(line);
    }
  }

  pay(): void {
    this.alertService.error('Fonctionnalité non implémentée hors scope du TP.', null);
  }
}
