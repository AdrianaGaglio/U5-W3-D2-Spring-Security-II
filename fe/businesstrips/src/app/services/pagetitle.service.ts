import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PagetitleService {
  constructor() {}

  title = new BehaviorSubject<string>('Admin dashboard');
}
