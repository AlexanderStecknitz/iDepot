import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {Register} from "../register/register.model";

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  headers = new HttpHeaders().set('Content-Type', 'application/json');
  currentUser = {};

  constructor(private httpClient: HttpClient, public router: Router) {
  }

  register(email: string, password: string, firstname: string, lastname: string) {
    let register = new Register(email, password, firstname, lastname);
    return this.httpClient
      .post("/auth/register", register)
      .subscribe(() => {
        this.router.navigate(["login"]);
    })
  }

  login(email: string, password:string) {
    const base64 = window.btoa(`${email}:${password}`);
    const basicAuth = `Basic ${base64}`;
    return this.httpClient
      .post<any>("/auth/login","", {
        headers: {"Authorization": `${basicAuth}`}
      })
      .subscribe((res: any) => {
        console.log("succeed")
        localStorage.setItem('access_token', res.token);
        this.router.navigate(["home"])
      })
  }

  logout() {
    let removeToken = localStorage.removeItem('access_token');
    if(removeToken == null) {
      this.router.navigate(["login"])
    }
  }

  getToken() {
    return localStorage.getItem('access_token');
  }

  get isLoggedIn(): boolean {
    let authToken = localStorage.getItem('access_token');
    return authToken !== null;
  }

  doLogout() {
    localStorage.removeItem('access_token');
  }

}
