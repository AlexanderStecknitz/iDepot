import { Component } from '@angular/core';
import { first, tap } from 'rxjs';
import { Share } from './share/shared/share';
import { ShareReadService } from './share/shared/shareRead.service';
import { OverviewComponent } from './overview/overview.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  constructor(
    private readonly shareReadService: ShareReadService,
  ) {
    console.debug("AppComponent.constructor()")
  }

  shares: Share[] = []

  suchen() {
    console.log('search')
    let result: Share[] 
    this.shareReadService
              .find()
              .pipe(
                first(),
                tap(result => this.shares = result),
              )
              .subscribe();
  }
}
