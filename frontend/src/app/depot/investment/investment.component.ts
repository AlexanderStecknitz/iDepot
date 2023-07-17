import {Component, Input, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {InvestmentService} from "./investment.service";
import {ActivatedRoute} from "@angular/router";
import {Investment} from "./investment.model";
import {Observable, Subscription} from "rxjs";
import {MatTableModule} from "@angular/material/table";

@Component({
  selector: 'iDepot-investment',
  standalone: true,
  imports: [CommonModule, MatTableModule],
  templateUrl: './investment.component.html',
  styleUrls: ['./investment.component.scss']
})
export class InvestmentComponent {

  @Input()
  public depotId = 0;

  public investments: Investment[] = []
  displayedColumns = [
    'isin',
    'amount',
    'buyPrice',
  ];

  constructor(
    private readonly investmentService: InvestmentService,
  ) {

    this.findAll()

  }

  findAll() {
    this.investmentService.findAll("0").subscribe(investments => {
      this.investments = investments;
    })
  }

}
