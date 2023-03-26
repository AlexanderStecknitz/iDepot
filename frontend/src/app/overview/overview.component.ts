import { Component } from "@angular/core";
import { ShareReadService } from "../share/shared/shareRead.service";
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
        private readonly shareReadService: ShareReadService,
    ) {
        console.log("OverviewComponent.constructor()")
    }
}