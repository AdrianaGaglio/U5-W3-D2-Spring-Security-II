import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable, switchMap } from 'rxjs';
import { AuthService } from './auth.service';
import { iAuthdata } from '../interfaces/iauthdata';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor() {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    const json = localStorage.getItem('authData');
    if (!json) return next.handle(request);

    const newRequest = request.clone({
      setHeaders: {
        Authorization: `Bearer ${JSON.parse(json).token}`,
      },
    });

    return next.handle(newRequest);
  }
}
