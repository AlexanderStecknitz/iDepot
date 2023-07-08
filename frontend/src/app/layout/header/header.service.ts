import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Depot} from "../../depot/depot.model";
import {AuthService} from "../../auth/auth.service";
import {User} from "../../share/model/user.model";

@Injectable({
  providedIn: 'root'
})
export class HeaderService {

  constructor(
    private readonly httpClient: HttpClient,
  ) { }

  findUser(): Observable<User> {
    return this.httpClient.get<User>("/auth/user");
  }

  findAllDepotsByEmail(email: String): Observable<Depot[]> {
    return this.httpClient.get<Depot[]>("/api/depot/" + email);
  }

}
