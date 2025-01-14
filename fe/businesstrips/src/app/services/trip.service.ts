import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { iTrip } from '../interfaces/itrip';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { IReservationresponse } from '../interfaces/ireservationresponse';

@Injectable({
  providedIn: 'root',
})
export class TripService {
  constructor(private http: HttpClient) {}

  url: string = environment.tripUrl;
  trips$ = new BehaviorSubject<iTrip[]>([]);
  tripsById$ = new BehaviorSubject<iTrip[]>([]);

  getTrips(): Observable<iTrip[]> {
    return this.http
      .get<iTrip[]>(this.url)
      .pipe(tap((trips) => this.trips$.next(trips)));
  }

  getById(id: number): Observable<iTrip> {
    return this.http.get<iTrip>(`${this.url}/${id}`);
  }

  delete(trip: iTrip): Observable<string> {
    return this.http
      .delete<string>(`${this.url}/${trip.id}`, {
        responseType: 'text' as 'json',
      })
      .pipe(tap((res) => this.getTrips().subscribe()));
  }

  create(trip: Partial<iTrip>): Observable<iTrip> {
    return this.http
      .post<iTrip>(this.url, trip)
      .pipe(tap((res) => this.getTrips().subscribe()));
  }

  update(trip: Partial<iTrip>): Observable<iTrip> {
    return this.http.put<iTrip>(`${this.url}/${trip.id}`, trip);
  }

  changeStatus(id: number, status: string): Observable<iTrip> {
    return this.http
      .put<iTrip>(`${this.url}/${id}/update?status=${status}`, status)
      .pipe(tap((result) => this.getTrips().subscribe()));
  }

  search(destination: string) {
    return this.http.get<iTrip[]>(
      `${this.url}/search?destination=${destination}`
    );
  }
}
