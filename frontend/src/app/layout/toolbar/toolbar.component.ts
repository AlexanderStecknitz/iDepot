import { Component, EventEmitter, Output } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { RouterLink } from "@angular/router";
import { AuthService } from "../../auth/auth.service";

@Component({
  selector: "iDepot-toolbar",
  standalone: true,
  imports: [
    CommonModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    RouterLink,
  ],
  templateUrl: "./toolbar.component.html",
  styleUrls: ["./toolbar.component.scss"],
})
export class ToolbarComponent {
  @Output() toggleSidenav = new EventEmitter<boolean>();

  isAuthenticated: boolean = this.authService.isLoggedIn;

  constructor(private readonly authService: AuthService) {}

  logout() {
    this.authService.logout();
  }
}
