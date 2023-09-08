import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { Investment } from '../investment.model';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'iDepot-investment-create-dialog',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
  ],
  templateUrl: './investment-create-dialog.component.html',
  styleUrls: ['./investment-create-dialog.component.scss'],
})
export class InvestmentCreateDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<InvestmentCreateDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public investment: Investment
  ) {}
}
