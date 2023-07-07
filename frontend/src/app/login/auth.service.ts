import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BaseAuthApiService, AuthResponseTokens } from '@dekh/ngx-jwt-auth';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Login, Registration } from "./login.model";

@Injectable({
  providedIn: 'root',
})
export class AuthApiService extends BaseAuthApiService {
  constructor(private readonly httpClient: HttpClient) {
    super();
  }

  readonly #baseUrl = "http://localhost:8080"

  // This method returns AuthResponseTokens which has the structure
  // { accessToken: string; refreshToken?: string; }, so you don't need to map anything!
  public login(credentials: Login): Observable<AuthResponseTokens> {
    console.log("auth service")
    return this.httpClient.post<AuthResponseTokens>(
      this.#baseUrl + '/auth/login',
      credentials,
      { withCredentials: true }
    );
  }

  public logout(): Observable<void> {
    return this.httpClient.post<void>(this.#baseUrl + '/auth/logout', null, {
      withCredentials: true,
    });
  }

  // Since this method does not return the model we need from the server,
  // we map it using the map operator in { accessToken: string; refreshToken?: string; }
  public refresh(): Observable<any> {
    return this.httpClient.post<any>(this.#baseUrl + '/auth/refresh', null, {
      withCredentials: true,
    }).pipe(
      map((res) => ({
        accessToken: res.tokens.newAccessToken,
        refreshToken: res.tokens.newRefreshToken,
      }))
    );
  }

  public register(credentials: Registration): Observable<void> {
    return this.httpClient.post<void>(this.#baseUrl + '/auth/register', credentials);
  }
}
