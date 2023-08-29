import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatTableModule} from "@angular/material/table";
import {Investment} from "../investment.model";
import {MatDialog} from "@angular/material/dialog";
import {InvestmentTableViewService} from "./investment-table-view.service";

@Component({
  selector: 'iDepot-investment-table-view',
  standalone: true,
  imports: [CommonModule, MatTableModule],
  templateUrl: './investment-table-view.component.html',
  styleUrls: ['./investment-table-view.component.scss']
})
export class InvestmentTableViewComponent {

  public investments: Investment[] = []

  displayedColumns = [
    'name',
    'isin',
    'amount',
    'buyPrice',
    'currentPrice',
    'yield',
  ];

  constructor(
      private readonly investmentService: InvestmentTableViewService,
  ) {

    this.findAll()

  }

  findAll() {
    this.investmentService.findAll("0").subscribe(investments => {
      this.investments = investments;
    })
  }

}
