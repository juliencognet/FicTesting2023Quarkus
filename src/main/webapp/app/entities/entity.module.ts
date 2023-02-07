import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.FicTests2023ProductModule),
      },
      {
        path: 'discount-code',
        loadChildren: () => import('./discount-code/discount-code.module').then(m => m.FicTests2023DiscountCodeModule),
      },
      {
        path: 'basket',
        loadChildren: () => import('./basket/basket.module').then(m => m.FicTests2023BasketModule),
      },
      {
        path: 'product-in-basket',
        loadChildren: () => import('./product-in-basket/product-in-basket.module').then(m => m.FicTests2023ProductInBasketModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class FicTests2023EntityModule {}
