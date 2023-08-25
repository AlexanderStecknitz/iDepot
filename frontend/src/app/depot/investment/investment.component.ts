import {Component, Input} from '@angular/core';
import { CommonModule } from '@angular/common';
import {InvestmentService} from "./investment.service";
import {Investment} from "./investment.model";
import {MatTableModule} from "@angular/material/table";
import {MatButtonModule} from "@angular/material/button";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {InvestmentCreateDialogComponent} from "./investment-create-dialog/investment-create-dialog.component";

@Component({
  selector: 'iDepot-investment',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, MatDialogModule],
  templateUrl: './investment.component.html',
  styleUrls: ['./investment.component.scss']
})
export class InvestmentComponent {

  @Input()
  public depotId = 0;

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
    private readonly investmentService: InvestmentService,
    public readonly dialog: MatDialog
  ) {

    this.findAll()

  }

  create() {
    const dialogRef = this.dialog.open(InvestmentCreateDialogComponent,
      {
        data: {
          isin: "Test",
          depotId: 0,
          amount: 0,
          buyPrice: 0
        }
      });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      this.investmentService.create(result).subscribe();
    });
  }

  findAll() {
    this.investmentService.findAll("0").subscribe(investments => {
      this.investments = investments;
    })
  }

}
