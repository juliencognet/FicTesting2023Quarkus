import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FicTests2023SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { ProductListComponent } from 'app/product-list/product-list.component';
import { BasketIconComponent } from 'app/basket-icon/basket-icon.component';
import { ProductBasketComponent } from 'app/product-basket/product-basket.component';

@NgModule({
  imports: [FicTests2023SharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent, ProductListComponent, BasketIconComponent, ProductBasketComponent],
})
export class FicTests2023HomeModule {}
