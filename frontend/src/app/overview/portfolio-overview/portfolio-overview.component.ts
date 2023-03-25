import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { first, tap } from "rxjs";
import { Aktie } from "src/app/aktie/shared/aktie";
import { AktieReadService } from "src/app/aktie/shared/aktieRead.service";
import { GefundeneInvestmentsComponent } from "./gefundene-investments.component";
import { PortfolioOverviewTitelComponent } from "./portfolio-overview-titel.component";

@Component({
    selector: 'portfolio-overview',
    templateUrl: './portfolio-overview.component.html',
    imports: [
        PortfolioOverviewTitelComponent,
        GefundeneInvestmentsComponent,
        CommonModule,
    ],
    standalone: true,
})
export class PortfolioOverviewComponent implements OnInit {
    constructor(
        private readonly aktieReadService: AktieReadService,
    ) {
        console.log('PortfolioOverviewComponent.constructor()')
    }

    aktien: Aktie[] = []

    ngOnInit() {
        this.suchen();
    }

    suchen() {
        console.log('GefundeneInvestmentsComponent.suchen()')
        this.aktieReadService.find()
            .pipe(
                first(),
                tap(result => this.aktien = result)
            )
            .subscribe()
    }
}