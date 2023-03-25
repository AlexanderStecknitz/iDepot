import { Component } from "@angular/core";
import { AktieReadService } from "../aktie/shared/aktieRead.service";
import { PortfolioOverviewComponent } from "./portfolio-overview/portfolio-overview.component";


@Component({
    selector: 'overview',
    templateUrl: './overview.component.html',
    imports: [
        PortfolioOverviewComponent,
    ],
    standalone: true,
})
export class OverviewComponent {
    constructor(
        private readonly aktieReadService: AktieReadService,
    ) {
        console.log("OverviewComponent.constructor()")
    }
}