import { Component } from '@angular/core';
import { PagetitleService } from '../../services/pagetitle.service';
import { EmployeeService } from '../../services/employee.service';
import { TripService } from '../../services/trip.service';
import { ReservationService } from '../../services/reservation.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
  constructor(
    private pageTitle: PagetitleService,
    private employeeSvc: EmployeeService,
    private tripSvc: TripService,
    private reservationSvc: ReservationService
  ) {}

  employees!: number;
  trips!: number;
  reservations!: number;

  ngOnInit() {
    this.pageTitle.title.next('Admin dashboard');

    this.employeeSvc.count().subscribe((count) => (this.employees = count));
    this.tripSvc.count().subscribe((count) => (this.trips = count));
    this.reservationSvc
      .count()
      .subscribe((count) => (this.reservations = count));
  }
}
