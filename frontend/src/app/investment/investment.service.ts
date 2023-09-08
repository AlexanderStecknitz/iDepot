import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Investment } from "./investment.model";

@Injectable({
  providedIn: "root",
})
export class InvestmentService {
  constructor(private readonly httpClient: HttpClient) {}

  create(investment: Investment) {
    return this.httpClient.post("/api/investment", investment);
  }
}
