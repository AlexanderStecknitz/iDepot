import { Share } from './share'

export interface ShareServer {
    isin: String
    wkn: String
    amount: Number
    price: Number
}

export const toShare = (shareServer: ShareServer) => {

    console.log(shareServer)

    const {
        isin,
        amount,
        price,
        wkn,
    } = shareServer

    const share: Share = {
        isin,
        price,
        amount,
        wkn,
    };
    console.log("Share.fromServer: share=", share);
    return share;
}