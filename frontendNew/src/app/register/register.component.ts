import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AuthService} from "../auth/auth.service";
import {FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";

@Component({
  selector: 'iDepot-register',
  standalone: true,
  imports: [CommonModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, MatButtonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required]);
  firstname = new FormControl('', [Validators.required]);
  lastname = new FormControl('', [Validators.required]);

  constructor(private authService: AuthService) {}

  register() {
    this.authService.register(
      this.email.getRawValue()!,
      this.password.getRawValue()!,
      this.firstname.getRawValue()!,
      this.lastname.getRawValue()!
    );
  }

}
