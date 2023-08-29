import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {InvestmentCompositionPieChartModel} from "./investment-composition-pie-chart.model";

@Injectable({
  providedIn: 'root'
})
export class InvestmentCompositionPieChartService {

  constructor(
      private readonly httpClient: HttpClient
  ) { }
  
  getCompositionPieChartData(depotId: string): Observable<InvestmentCompositionPieChartModel[]> {
    return this.httpClient.get<InvestmentCompositionPieChartModel[]>("/api/chart/composition/" + depotId);
  }
  
}
