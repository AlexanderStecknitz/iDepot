import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, tap} from "rxjs";
import {Depot} from "../../depot/depot.model";

@Injectable({
  providedIn: 'root'
})
export class MainService {

  constructor(
    private httpClient: HttpClient
  ) {
  }

  findAllDepotsByEmail(email: string): Observable<Depot[]> {
    return this.httpClient.get<Depot[]>("/api/depot/" + email);
  }
}
