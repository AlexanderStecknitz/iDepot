import { Injectable } from "@angular/core";
import { SharePosition } from "./shareposition.model";
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, first, throwError } from "rxjs";
import { catchError } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class SharePositionService {

    readonly #baseUrl = "http://localhost:8080/api/position/share"

    constructor(
        private readonly httpClient: HttpClient
    ) {
        console.log("Servide start");
    }

    findAll(): Observable<SharePosition[]> {
        return (
            this.httpClient
                .get<SharePosition[]>(this.#baseUrl)
                .pipe(
                    catchError(this.handleError),
                    first()
                    )
            )
    }

    create(sharePosition: SharePosition): Observable<SharePosition> {
        return this.httpClient
            .post<SharePosition>(this.#baseUrl, sharePosition, {})
    }

    private handleError(error: HttpErrorResponse) {
        if (error.status === 0) {
          console.error('An error occurred:', error.error);
        } else {
          console.error(
            `Backend returned code ${error.status}, body was: `, error.error);
        }
        return throwError(() => new Error('Something bad happened; please try again later.'));
      }

}