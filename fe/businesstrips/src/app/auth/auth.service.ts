import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { JwtHelperService } from '@auth0/angular-jwt';
import { BehaviorSubject, map, Observable, tap } from 'rxjs';
import { iAuthdata } from '../interfaces/iauthdata';
import { Router } from '@angular/router';
import { iLoginrequest } from '../interfaces/iloginrequest';
import { iRegisterrequest } from '../interfaces/iregisterrequest';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient, private router: Router) {}

  url: string = environment.authUrl;

  jwtHelper: JwtHelperService = new JwtHelperService();

  authSubject$ = new BehaviorSubject<iAuthdata | null>(null);

  user$ = this.authSubject$.asObservable().pipe(
    tap((authData) => this.isLoggedIn == !!authData),
    map((authData) => authData?.user)
  );

  isLoggedIn$ = this.authSubject$.pipe(map((authData) => !!authData));

  isLoggedIn: boolean = false;

  autoLogoutTimer: any;

  register(newUser: iRegisterrequest): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(`${this.url}/register`, newUser);
  }

  login(authData: iLoginrequest): Observable<iAuthdata> {
    return this.http.post<iAuthdata>(`${this.url}/login`, authData).pipe(
      tap((authData) => {
        this.authSubject$.next(authData);
        localStorage.setItem('authData', JSON.stringify(authData));

        const expDate = this.jwtHelper.getTokenExpirationDate(authData.token);

        if (!expDate) return;

        this.autoLogout(expDate);
      })
    );
  }

  autoLogout(expDate: Date) {}

  logout() {
    this.authSubject$.next(null);
    localStorage.removeItem('authData');
    this.router.navigate(['/auth/login']);
  }

  restoreUser() {
    const userJson: string | null = localStorage.getItem('authData');
    if (!userJson) return;

    const authData: iAuthdata = JSON.parse(userJson);

    if (this.jwtHelper.isTokenExpired(authData.token)) {
      this.logout();
      return;
    }

    this.authSubject$.next(authData);
  }
}