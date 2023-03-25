import { Aktie } from './aktie'

export interface AktieServer {
    id?: string
    isin: String
    name: String
    kaufkurs: Number
    anzahl: Number
    wkn: String
    boerse: String
}

export const toAktie = (aktieServer: AktieServer) => {

    console.log(aktieServer)

    const {
        id,
        isin,
        name,
        kaufkurs,
        anzahl,
        wkn,
        boerse,
    } = aktieServer

    const aktie: Aktie = {
        id,
        isin,
        name,
        kaufkurs,
        anzahl,
        wkn,
        boerse,
    };
    console.log("Aktie.fromServer: aktie=", aktie);
    return aktie;
}