import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FicTests2023SharedModule } from 'app/shared/shared.module';
import { ProductInBasketComponent } from './product-in-basket.component';
import { ProductInBasketDetailComponent } from './product-in-basket-detail.component';
import { ProductInBasketUpdateComponent } from './product-in-basket-update.component';
import { ProductInBasketDeleteDialogComponent } from './product-in-basket-delete-dialog.component';
import { productInBasketRoute } from './product-in-basket.route';

@NgModule({
  imports: [FicTests2023SharedModule, RouterModule.forChild(productInBasketRoute)],
  declarations: [
    ProductInBasketComponent,
    ProductInBasketDetailComponent,
    ProductInBasketUpdateComponent,
    ProductInBasketDeleteDialogComponent,
  ],
  entryComponents: [ProductInBasketDeleteDialogComponent],
})
export class FicTests2023ProductInBasketModule {}
