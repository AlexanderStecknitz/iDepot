import { Component } from "@angular/core";
import {MatSidenavModule} from '@angular/material/sidenav';

@Component({
    selector: 'DrawerComponent',
    templateUrl: './drawer.component.html',
    styleUrls: [
        './drawer.component.scss'
    ],
    imports: [
        MatSidenavModule,
    ],
    standalone: true,
})
export class DrawerComponent {
    constructor() {
        console.log('DrawerComponent.constructor()')
    }
}