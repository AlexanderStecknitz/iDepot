import { Injectable } from "@angular/core";
import {
    HttpClient,
} from '@angular/common/http';
import { first, map, Observable } from "rxjs";
import { Share } from "./share";
import { ShareServer, toShare } from "./shareServer";

@Injectable({ providedIn: 'root'})
export class ShareReadService {
    readonly #baseUrl = "http://localhost:8080/api/share/all"

    constructor(private readonly httpClient: HttpClient) {
        console.log("ShareReadService.constructor: baseUrl=", this.#baseUrl)
    }

    find(): Observable<Share[]> {
        console.log("Test")
        return (
            this.httpClient
                .get<ShareServer[]>(this.#baseUrl)
                .pipe(
                    first(),
                    map(result => this.#toShareArray(result)),
                )
        )
    }

    #toShareArray(res: ShareServer[]) {
        console.log(res)
        const shares = res.map(shareServer =>
            toShare(shareServer),
        );
        console.log('ShareReadService.findAll(): share=', shares);
        return shares;
    }
}