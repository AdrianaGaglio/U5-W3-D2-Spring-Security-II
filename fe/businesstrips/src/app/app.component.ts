import { Component, signal } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { PagetitleService } from './services/pagetitle.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  constructor(private pageTitle: PagetitleService) {}

  ngOnInit() {
    this.pageTitle.title.next('Admin dashboard');
  }
}
