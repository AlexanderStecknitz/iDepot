import { Component, Input } from "@angular/core";
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from "@angular/common";
import { MatTableModule } from '@angular/material/table';
import { AktieReadService } from "src/app/aktie/shared/aktieRead.service";
import { Aktie } from "src/app/aktie/shared/aktie";

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
export class GefundeneInvestmentsComponent {
    constructor(
        private readonly aktieReadService: AktieReadService,
    ) {
        console.log('GefundeneInvestmentsComponent.constructor()')
    }

    @Input() gefundeneAktien: Aktie[] = []

    displayedColumns: string[] = [
        'name',
        'isin',
        'wkn',
        'kaufkurs',
        'anzahl',
        'boerse',
    ];
}