import { Share } from './share'

export interface ShareServer {
    isin: String
    name: String
    wkn: String
    amount: Number
    price: Number
}

export const toShare = (shareServer: ShareServer) => {

    console.log(shareServer)

    const {
        isin,
        name,
        amount,
        price,
        wkn,
    } = shareServer

    const share: Share = {
        isin,
        name,
        price,
        amount,
        wkn,
    };
    console.log("Share.fromServer: share=", share);
    return share;
}