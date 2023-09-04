import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Transaction} from "./transaction-history.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TransactionHistoryService {

  constructor(private readonly httpClient: HttpClient) { }

  findAll(depotId: number): Observable<Transaction[]> {
    return this.httpClient.get<Transaction[]>("/api/investment/transaction-history/" + depotId)
  }

}
