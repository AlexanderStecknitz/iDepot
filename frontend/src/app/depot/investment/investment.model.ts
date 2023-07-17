import {Stock} from "../../share/model/stock.model";

export interface Investment {
  isin: string;
  depotId: number;
  amount: number;
  buyPrice: number;
}
