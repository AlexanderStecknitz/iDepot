import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DepotService {
  private depotId: number;

  constructor() {
    this.depotId = -1;
  }

  getDepotId(): number {
    return this.depotId;
  }

  setDepotId(depotId: number): void {
    this.depotId = depotId;
  }
}
