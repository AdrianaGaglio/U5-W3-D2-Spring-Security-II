import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { iReservation } from '../interfaces/ireservation';
import { environment } from '../../environments/environment.development';
import { iReservationrequest } from '../interfaces/ireservationrequest';
import { IReservationresponse } from '../interfaces/ireservationresponse';
import { iPreference } from '../interfaces/ipreference';
import { iAddpreference } from '../interfaces/iaddpreference';

@Injectable({
  providedIn: 'root',
})
export class ReservationService {
  constructor(private http: HttpClient) {}

  url: string = environment.reservationUrl;
  reservations$ = new BehaviorSubject<iReservation[] | IReservationresponse[]>(
    []
  );

  getReservations(): Observable<IReservationresponse[]> {
    return this.http
      .get<IReservationresponse[]>(this.url)
      .pipe(tap((reservations) => this.reservations$.next(reservations)));
  }

  getByEmployee(employeeId: number): Observable<IReservationresponse[]> {
    return this.http.get<IReservationresponse[]>(
      `${this.url}/employee/${employeeId}`
    );
  }

  addReservation(
    resRequest: iReservationrequest
  ): Observable<IReservationresponse> {
    return this.http
      .post<IReservationresponse>(this.url, resRequest)
      .pipe(tap((res) => this.getReservations().subscribe()));
  }

  delete(id: number): Observable<string> {
    return this.http
      .delete<string>(`${this.url}/${id}`)
      .pipe(tap((result) => this.getReservations().subscribe()));
  }

  addPreference(
    id: number,
    preference: iAddpreference
  ): Observable<IReservationresponse> {
    return this.http
      .put<IReservationresponse>(`${this.url}/${id}/preference`, preference)
      .pipe(tap((res) => this.getReservations().subscribe()));
  }

  getByTrip(tripId: number): Observable<IReservationresponse[]> {
    return this.http.get<IReservationresponse[]>(`${this.url}/trip/${tripId}`);
  }

  searchByDate(date: string): Observable<IReservationresponse[]> {
    return this.http.get<IReservationresponse[]>(
      `${this.url}/byDate?date=${date}`
    );
  }

  searchByDestination(destination: string): Observable<IReservationresponse[]> {
    return this.http.get<IReservationresponse[]>(
      `${this.url}/byDestination?destination=${destination}`
    );
  }

  search(
    destination: string,
    date: string
  ): Observable<IReservationresponse[]> {
    return this.http.get<IReservationresponse[]>(
      `${this.url}/search?destination=${destination}&date=${date}`
    );
  }

  count(): Observable<number> {
    return this.http.get<number>(`${this.url}/count`);
  }
}
