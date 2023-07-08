import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatSelectChange, MatSelectModule} from "@angular/material/select";
import {Router} from "@angular/router";
import {Depot} from "../../depot/depot.model";
import {HeaderService} from "./header.service";
import {MatToolbarModule} from "@angular/material/toolbar";

@Component({
  selector: 'iDepot-header',
  standalone: true,
  imports: [CommonModule, MatSelectModule, MatToolbarModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  public depots: Depot[] = []

  public depotId: number = 0;

  constructor(
    private router: Router,
    private headerService: HeaderService
  ) {
  }

  ngOnInit(): void {
    this.headerService.findUser().subscribe(user => {
      this.headerService.findAllDepotsByEmail(user.email).subscribe(depots => {
        this.depots = depots;
        }
      )
    })
  }

  changeDepot($event: MatSelectChange) {
    this.router.navigate(["/main/" + $event.value])
  }

}
