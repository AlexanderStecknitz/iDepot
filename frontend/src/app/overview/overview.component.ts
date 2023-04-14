import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharePosition } from './shareposition.model';
import { SharePositionService } from './shareposition.service';
import { first, tap } from 'rxjs';
import { HttpClientModule } from '@angular/common/http';
import { SharePositionViewComponent } from './share-position-view/share-position-view.component';
import { MatButtonModule } from '@angular/material/button';
import { AddTransactionComponent } from './add-transaction/add-transaction.component';

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [CommonModule, HttpClientModule, SharePositionViewComponent, MatButtonModule, AddTransactionComponent],
  providers: [SharePositionService],
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {

  sharePositions: SharePosition[] = [];
  
  constructor(
    private readonly sharePositionService: SharePositionService
  ) {}
  
  ngOnInit(): void {
    this.search();
  }

  search() {
    this.sharePositionService.findAll()
            .pipe(
                first(),
                tap(result => this.sharePositions = result)
            )
            .subscribe()
  }

}
