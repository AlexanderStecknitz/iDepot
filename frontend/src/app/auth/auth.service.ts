import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { Register } from './register/register.model';
import { LoginResult } from './auth.model';
import { DepotService } from '../share/depot.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  headers = new HttpHeaders().set('Content-Type', 'application/json');

  private depotId: number = this.depotService.getDepotId();

  constructor(
    private httpClient: HttpClient,
    public router: Router,
    private depotService: DepotService
  ) {}

  get isLoggedIn(): boolean {
    const authToken = localStorage.getItem('access_token');
    return authToken !== null;
  }

  register(
    email: string,
    password: string,
    firstname: string,
    lastname: string
  ) {
    const register = new Register(email, password, firstname, lastname);
    return this.httpClient.post('/auth/register', register).subscribe(() => {
      this.router.navigate(['login']);
    });
  }

  login(email: string, password: string) {
    const base64 = window.btoa(`${email}:${password}`);
    const basicAuth = `Basic ${base64}`;
    return this.httpClient
      .post<LoginResult>('/auth/login', '', {
        headers: { Authorization: `${basicAuth}` },
      })
      .subscribe((res: LoginResult) => {
        localStorage.setItem('access_token', res.token);
        localStorage.setItem('email', res.email);
        localStorage.setItem('depot', '0');
        this.depotService.setDepotId(0);
        this.router.navigate(['main'], { queryParams: { email: res.email } });
      });
  }

  logout() {
    const removeToken = localStorage.removeItem('access_token');
    if (removeToken == null) {
      this.router.navigate(['login']);
    }
  }

  getToken() {
    return localStorage.getItem('access_token');
  }

  getDepotId() {
    return this.depotId;
  }
}
