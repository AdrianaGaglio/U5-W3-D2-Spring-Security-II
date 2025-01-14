import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmployeeService } from '../../services/employee.service';
import { iEmployee } from '../../interfaces/iemployee';
import { ReservationService } from '../../services/reservation.service';
import { iReservation } from '../../interfaces/ireservation';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrl: './employee.component.scss',
})
export class EmployeeComponent {
  constructor(
    private route: ActivatedRoute,
    private employeeSvc: EmployeeService,
    private reservationSvc: ReservationService
  ) {}

  employee!: iEmployee;
  reservations!: iReservation[];

  ngOnInit() {
    this.route.params.subscribe((params: any) => {
      let id = +params['id'];
      this.employeeSvc.getById(id).subscribe((employee: iEmployee) => {
        this.employee = employee;
        this.reservationSvc
          .getByEmployee(employee.id)
          .subscribe((reservation: iReservation[]) => {
            this.reservations = reservation;
            console.log(this.reservations);
          });
      });
    });
  }
}
