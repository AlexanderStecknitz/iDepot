export class Stock {
  isin: string;
  name: string;
  wkn: string;
  actualPrice: number;

  constructor(isin: string, name: string, wkn: string, actualPrice: number) {
    this.isin = isin;
    this.name = name;
    this.wkn = wkn;
    this.actualPrice = actualPrice;
  }


}
