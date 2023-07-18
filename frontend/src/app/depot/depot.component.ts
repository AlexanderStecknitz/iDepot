import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {InvestmentComponent} from "./investment/investment.component";

@Component({
  selector: 'iDepot-depot',
  standalone: true,
  imports: [CommonModule, InvestmentComponent],
  templateUrl: './depot.component.html',
  styleUrls: ['./depot.component.scss']
})
export class DepotComponent {

  depotId = 0;

}
