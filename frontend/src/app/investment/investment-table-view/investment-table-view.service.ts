import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Investment } from '../investment.model';

@Injectable({
  providedIn: 'root',
})
export class InvestmentTableViewService {
  constructor(private readonly httpClient: HttpClient) {}

  create(investment: Investment) {
    return this.httpClient.post('/api/investment', investment);
  }

  findAll(depotId: string): Observable<Investment[]> {
    return this.httpClient.get<Investment[]>('/api/investment/' + depotId);
  }
}
