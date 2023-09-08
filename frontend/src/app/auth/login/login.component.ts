import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgModel, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from '../auth.service';
import { MatCardModule } from '@angular/material/card';
import { RouterLink } from '@angular/router';
import { Login } from './login.model';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'iDepot-login',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCardModule,
    RouterLink,
    MatDividerModule,
    MatIconModule,
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  loginForm: Login = { email: '', password: '' };

  constructor(private readonly authService: AuthService) {}

  login() {
    this.authService.login(this.loginForm.email, this.loginForm.password);
  }

  getErrorMessage(field: NgModel) {
    if (field.name == 'email') {
      return 'Email is invaild';
    }
    return 'Password is invalid';
  }
}
