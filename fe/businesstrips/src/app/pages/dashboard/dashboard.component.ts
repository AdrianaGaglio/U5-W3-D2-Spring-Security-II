import { Component } from '@angular/core';
import { PagetitleService } from '../../services/pagetitle.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
  constructor(private pageTitle: PagetitleService) {}

  ngOnInit() {
    this.pageTitle.title.next('Admin dashboard');
  }
}
