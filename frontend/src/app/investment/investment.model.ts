import {Stock} from "../share/model/stock.model";

export interface Investment {
  isin: string;
  name: string;
  depotId: number;
  amount: number;
  buyPrice: number;
  yield: number;
  currentPrice: number;
}
