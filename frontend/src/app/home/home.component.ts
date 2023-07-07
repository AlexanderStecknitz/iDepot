import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {HttpClient, HttpClientModule} from "@angular/common/http";

@Component({
  selector: 'iDepot-home',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private httpClient: HttpClient) {}

  ngOnInit(): void {
    this.test();
  }

  test() {
    this.httpClient.get("/api/share").subscribe(res => console.log(res))
  }

}
