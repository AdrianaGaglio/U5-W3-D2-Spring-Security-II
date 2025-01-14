import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UploadService {
  constructor(private http: HttpClient) {}

  url: string = environment.uploadUrl;

  upload(formData: FormData): Observable<string> {
    return this.http.post<string>(this.url, formData, {
      responseType: 'text' as 'json',
    });
  }
}
