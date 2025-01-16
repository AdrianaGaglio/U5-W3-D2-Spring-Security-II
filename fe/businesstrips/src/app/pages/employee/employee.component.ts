import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmployeeService } from '../../services/employee.service';
import { iEmployee } from '../../interfaces/iemployee';
import { ReservationService } from '../../services/reservation.service';
import { iReservation } from '../../interfaces/ireservation';
import { PagetitleService } from '../../services/pagetitle.service';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrl: './employee.component.scss',
})
export class EmployeeComponent {
  constructor(
    private route: ActivatedRoute,
    private employeeSvc: EmployeeService,
    private reservationSvc: ReservationService,
    private pageTitle: PagetitleService
  ) {}

  employee!: iEmployee;
  reservations!: iReservation[];

  ngOnInit() {
    this.pageTitle.title.next('Employee details');
    this.route.params.subscribe((params: any) => {
      let id = +params['id'];
      this.employeeSvc.getById(id).subscribe((employee: iEmployee) => {
        this.employee = employee;
        this.reservationSvc
          .getByEmployee(employee.id)
          .subscribe((reservation: iReservation[]) => {
            this.reservations = reservation;
          });
      });
    });
  }
}
