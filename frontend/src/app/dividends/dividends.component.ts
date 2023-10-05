import { Component } from '@angular/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core';

@Component({
  selector: 'iDepot-dividends',
  standalone: true,
  imports: [MatDatepickerModule, MatCardModule, MatNativeDateModule],
  templateUrl: './dividends.component.html',
  styleUrls: ['./dividends.component.scss'],
})
export class DividendsComponent {}
