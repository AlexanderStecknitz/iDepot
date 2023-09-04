import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {TransactionHistoryService} from "./transaction-history.service";
import {Transaction} from "./transaction-history.model";
import {MatTableModule} from "@angular/material/table";

@Component({
  selector: 'iDepot-transaction-history',
  standalone: true,
  imports: [CommonModule, MatTableModule],
  templateUrl: './transaction-history.component.html',
  styleUrls: ['./transaction-history.component.scss']
})
export class TransactionHistoryComponent implements OnInit {

  constructor(private readonly transactionService: TransactionHistoryService) {}

  transactions: Transaction[] = [];

  depotId: number = 0;

  displayedColumns = [
      'created',
      'stock_name',
      'buy_price',
      'amount',
      'investment_value',
  ]

  ngOnInit(): void {

    this.findAll();

  }

  findAll() {
    this.transactionService.findAll(this.depotId).subscribe(result => {
      this.transactions = result;
    });
  }

}
