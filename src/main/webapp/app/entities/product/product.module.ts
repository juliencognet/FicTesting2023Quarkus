import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FicTests2023SharedModule } from 'app/shared/shared.module';
import { ProductComponent } from './product.component';
import { ProductDetailComponent } from './product-detail.component';
import { ProductUpdateComponent } from './product-update.component';
import { ProductDeleteDialogComponent } from './product-delete-dialog.component';
import { productRoute } from './product.route';

@NgModule({
  imports: [FicTests2023SharedModule, RouterModule.forChild(productRoute)],
  declarations: [ProductComponent, ProductDetailComponent, ProductUpdateComponent, ProductDeleteDialogComponent],
  entryComponents: [ProductDeleteDialogComponent],
})
export class FicTests2023ProductModule {}
