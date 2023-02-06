import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { FicTests2023SharedModule } from 'app/shared/shared.module';
import { FicTests2023CoreModule } from 'app/core/core.module';
import { FicTests2023AppRoutingModule } from './app-routing.module';
import { FicTests2023HomeModule } from './home/home.module';
import { FicTests2023EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    FicTests2023SharedModule,
    FicTests2023CoreModule,
    FicTests2023HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    FicTests2023EntityModule,
    FicTests2023AppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class FicTests2023AppModule {}
