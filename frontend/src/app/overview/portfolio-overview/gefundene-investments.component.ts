import { Component, Input } from "@angular/core";
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from "@angular/common";
import { MatTableModule } from '@angular/material/table';
import { ShareReadService } from "src/app/share/shared/shareRead.service";
import { Share } from "src/app/share/shared/share";

@Component({
    selector: 'GefundeneInvesmentsComponent',
    templateUrl: './gefundene-investments.component.html',
    imports: [
        MatCardModule,
        CommonModule,
        MatTableModule,
    ],
    standalone: true,
})
export class FoundSharesComponent {
    constructor(
        private readonly shareReadService: ShareReadService,
    ) {
        console.log('FoundSharesComponent.constructor()')
    }

    @Input() foundShares: Share[] = []

    displayedColumns: string[] = [
        'isin',
        'wkn',
        'price',
        'amount',
    ];
}