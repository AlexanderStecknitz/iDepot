import { Component } from '@angular/core';
import { first, tap } from 'rxjs';
import { Aktie } from './aktie/shared/aktie';
import { AktieReadService } from './aktie/shared/aktieRead.service';
import { OverviewComponent } from './overview/overview.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  constructor(
    private readonly aktieReadService: AktieReadService,
  ) {
    console.debug("AppComponent.constructor()")
  }

  aktien: Aktie[] = []

  suchen() {
    console.log('Suchen')
    let result: Aktie[] 
    this.aktieReadService
              .find()
              .pipe(
                first(),
                tap(result => this.aktien = result),
              )
              .subscribe();
  }
}
