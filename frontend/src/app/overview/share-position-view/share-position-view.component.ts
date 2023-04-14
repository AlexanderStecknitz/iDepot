import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from "@angular/common";
import { MatTableModule } from '@angular/material/table';
import { SharePosition } from '../shareposition.model';;


@Component({
  selector: 'app-share-position-view',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule],
  templateUrl: './share-position-view.component.html',
  styleUrls: ['./share-position-view.component.scss']
})
export class SharePositionViewComponent {
  @Input() sharePositions: SharePosition[] = []
  displayedColumns = [
    'isin',
    'name',
    'wkn',
    'actual_price',
    "amount",
    "buy_price",
  ]
}
