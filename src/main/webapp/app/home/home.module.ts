import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FicTests2023SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [FicTests2023SharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class FicTests2023HomeModule {}
