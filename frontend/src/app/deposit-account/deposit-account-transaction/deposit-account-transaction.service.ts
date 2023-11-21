import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DepositAccountTransaction } from './deposit-account-transaction.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DepositAccountTransactionService {
  private ENDPOINT: string = '/api/deposit-account-transaction';

  constructor(private readonly httpClient: HttpClient) {}

  getDepositAccountTransactionByDepositAccountId(
    depositAccountId: number
  ): Observable<DepositAccountTransaction[]> {
    return this.httpClient.get<DepositAccountTransaction[]>(
      `${this.ENDPOINT}/${depositAccountId}`
    );
  }
}
