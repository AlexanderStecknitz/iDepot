import {Component, Input} from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatTableModule} from "@angular/material/table";
import {MatButtonModule} from "@angular/material/button";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {InvestmentCreateDialogComponent} from "./investment-create-dialog/investment-create-dialog.component";
import {InvestmentTableViewComponent} from "./investment-table-view/investment-table-view.component";
import {InvestmentService} from "./investment.service";

@Component({
  selector: 'iDepot-investment',
  standalone: true,
    imports: [CommonModule, MatTableModule, MatButtonModule, MatDialogModule, InvestmentTableViewComponent],
  templateUrl: './investment.component.html',
  styleUrls: ['./investment.component.scss']
})
export class InvestmentComponent {

  @Input()
  public depotId = 0;

  constructor(
    private readonly investmentService: InvestmentService,
    public readonly dialog: MatDialog
  ) {}

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

}
