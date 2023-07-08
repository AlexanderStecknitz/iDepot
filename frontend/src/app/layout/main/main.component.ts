import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ActivatedRoute, RouterLink, RouterOutlet} from "@angular/router";
import {MatIconModule} from "@angular/material/icon";
import {MatListModule} from "@angular/material/list";
import {MatSidenavModule} from "@angular/material/sidenav";
import {AuthService} from "../../auth/auth.service";
import {Subscription} from "rxjs";
import {HeaderComponent} from "../header/header.component";

@Component({
  selector: 'iDepot-main',
  standalone: true,
  imports: [CommonModule, RouterOutlet, MatIconModule, MatListModule, MatSidenavModule, RouterLink, HeaderComponent],
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent {

  private routeSubscription: Subscription;
  public depotId: string | undefined;

  constructor(
    private authService: AuthService,
    private actRoute: ActivatedRoute,
  ) {
    this.routeSubscription = this.actRoute.paramMap.subscribe(params => {
      this.depotId = params.get("depotId") ?? '';
    });
  }

  logout() {
    this.authService.logout()
  }
}
