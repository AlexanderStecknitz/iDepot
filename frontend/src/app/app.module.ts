import { NgModule } from '@angular/core';
import { BrowserModule, Title } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AktieReadService } from './aktie/shared/aktieRead.service';
import { HttpClientModule } from '@angular/common/http';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MainComponent } from './layout/main/main.component';
import { OverviewComponent } from './overview/overview.component';
import { DrawerComponent } from './layout/drawer/drawer.component';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatSlideToggleModule,
    MatSidenavModule,
    MainComponent,
    OverviewComponent,
    DrawerComponent,
  ],
  providers: [AktieReadService],
  bootstrap: [AppComponent]
})
export class AppModule { }
