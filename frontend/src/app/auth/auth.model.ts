import {Depot} from "../depot/depot.model";

export interface LoginResult {
  token: string,
  depots: Depot[],
}
