import {Component} from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink, RouterOutlet} from "@angular/router";
import {MatIconModule} from "@angular/material/icon";
import {MatListModule} from "@angular/material/list";
import {MatSidenavModule} from "@angular/material/sidenav";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'iDepot-main',
  standalone: true,
  imports: [CommonModule, RouterOutlet, MatIconModule, MatListModule, MatSidenavModule, RouterLink],
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent {

  email: string = ""

  constructor(
    private authService: AuthService,
  ) {}

  logout() {
    this.authService.logout()
  }

}
