import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { DxPieChartModule } from "devextreme-angular";
import { InvestmentCompositionPieChartService } from "./investment-composition-pie-chart.service";
import { InvestmentCompositionPieChartModel } from "./investment-composition-pie-chart.model";

@Component({
  selector: "iDepot-investment-composition-pie-chart",
  standalone: true,
  imports: [CommonModule, DxPieChartModule],
  templateUrl: "./investment-composition-pie-chart.component.html",
  styleUrls: ["./investment-composition-pie-chart.component.scss"],
})
export class InvestmentCompositionPieChartComponent {
  investments: InvestmentCompositionPieChartModel[] = [];

  constructor(
    private readonly investmentPieChartService: InvestmentCompositionPieChartService,
  ) {
    this.getPieChartData();
  }

  pieChartLabelText(pointInfo: any) {
    return pointInfo.argument + " " + pointInfo.value + "%";
  }

  getPieChartData() {
    this.investmentPieChartService
      .getCompositionPieChartData("0")
      .subscribe((result) => {
        this.investments = result;
      });
  }
}
