import { Share } from "./share.model";

export interface SharePosition {
    depotId: number,
    share: Share,
    amount: number,
    buyPrice: number
}