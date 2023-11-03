import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TransactionHistoryService } from './transaction-history.service';
import { Transaction } from './transaction-history.model';
import { MatTableModule } from '@angular/material/table';

@Component({
  selector: 'iDepot-transaction-history',
  standalone: true,
  imports: [CommonModule, MatTableModule],
  templateUrl: './transaction-history.component.html',
  styleUrls: ['./transaction-history.component.scss'],
})
export class TransactionHistoryComponent implements OnInit {
  transactions: Transaction[] = [];
  depotId = 0;
  displayedColumns = [
    'transaction_type',
    'stock_name',
    'buy_price',
    'amount',
    'investment_value',
    'created',
  ];

  constructor(private readonly transactionService: TransactionHistoryService) {}

  ngOnInit(): void {
    this.findAll();
  }

  findAll() {
    this.transactionService.findAll(this.depotId).subscribe(result => {
      this.transactions = result;
    });
  }
}
