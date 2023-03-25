import { Component } from "@angular/core";
import { AppRoutingModule } from "src/app/app-routing.module";

@Component({
    standalone: true,
    selector: 'finanzen-main',
    styleUrls: ['./main.component.scss'],
    templateUrl: './main.component.html',
    imports: [
        AppRoutingModule
    ]
})
export class MainComponent {
    constructor() {
        console.log("MainComponent.constructor()")
    }
}