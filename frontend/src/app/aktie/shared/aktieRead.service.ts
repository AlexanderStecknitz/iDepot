import { Injectable } from "@angular/core";
import {
    HttpClient,
} from '@angular/common/http';
import { first, map, Observable } from "rxjs";
import { Aktie } from "./aktie";
import { AktieServer, toAktie } from "./aktieServer";

@Injectable({ providedIn: 'root'})
export class AktieReadService {
    readonly #baseUrl = "http://localhost:8080/api/aktie/all"

    constructor(private readonly httpClient: HttpClient) {
        console.log("AktieReadService.constructor: baseUrl=", this.#baseUrl)
    }

    find(): Observable<Aktie[]> {
        console.log("Test")
        return (
            this.httpClient
                .get<AktieServer[]>(this.#baseUrl)
                .pipe(
                    first(),
                    map(result => this.#toAktieArray(result)),
                )
        )
    }

    #toAktieArray(res: AktieServer[]) {
        console.log(res)
        const aktien = res.map(aktieServer =>
            toAktie(aktieServer),
        );
        console.log('AktienReadService.findAll(): aktien=', aktien);
        return aktien;
    }
}