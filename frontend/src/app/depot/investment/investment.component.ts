import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {InvestmentService} from "./investment.service";

@Component({
  selector: 'iDepot-investment',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './investment.component.html',
  styleUrls: ['./investment.component.scss']
})
export class InvestmentComponent {

}
