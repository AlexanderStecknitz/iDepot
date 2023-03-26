import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { first, tap } from "rxjs";
import { Share } from "src/app/share/shared/share";
import { ShareReadService } from "src/app/share/shared/shareRead.service";
import { FoundSharesComponent } from "./gefundene-investments.component";
import { PortfolioOverviewTitelComponent } from "./portfolio-overview-titel.component";

@Component({
    selector: 'portfolio-overview',
    templateUrl: './portfolio-overview.component.html',
    imports: [
        PortfolioOverviewTitelComponent,
        FoundSharesComponent,
        CommonModule,
    ],
    standalone: true,
})
export class PortfolioOverviewComponent implements OnInit {
    constructor(
        private readonly shareReadService: ShareReadService,
    ) {
        console.log('PortfolioOverviewComponent.constructor()')
    }

    shares: Share[] = []

    ngOnInit() {
        this.search();
    }

    search() {
        console.log('FoundSharesComponent.search()')
        this.shareReadService.find()
            .pipe(
                first(),
                tap(result => this.shares = result)
            )
            .subscribe()
    }
}