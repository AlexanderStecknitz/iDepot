import { Component, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AddTransactionDialogComponent } from './add-transaction-dialog/add-transaction-dialog.component';
import { SharePosition } from "../shareposition.model";
import { SharePositionService } from '../shareposition.service';
import { Share } from '../share.model';

@Component({
  selector: 'app-add-transaction',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatDialogModule],
  templateUrl: './add-transaction.component.html',
  styleUrls: ['./add-transaction.component.scss']
})
export class AddTransactionComponent {

  isin: string = '';
  amount: number = 0;
  buyPrice: number = 0;

  constructor(
    public dialog: MatDialog,
    private sharePositionService: SharePositionService) {}

  openAddTransactionDialog(): void {
    const dialogRef = this.dialog.open(AddTransactionDialogComponent,{
      data: {
        isin: this.isin,
        amount: this.amount,
        buyPrice: this.buyPrice,
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.isin = result.isin;
      this.amount = result.amount;
      this.buyPrice = result.buyPrice;
      const share: Share = {
        isin: this.isin
      }

      const sharePosition: SharePosition = {
        share: share,
        depotId: 1,
        amount: this.amount,
        buyPrice: this.buyPrice,
      }
      this.sharePositionService.create(sharePosition).subscribe();
    }
      
    );
  }

}
