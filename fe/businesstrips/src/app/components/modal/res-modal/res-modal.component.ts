import { iPreference } from './../../../interfaces/ipreference';
import { Component, Input } from '@angular/core';
import { iReservation } from '../../../interfaces/ireservation';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ReservationService } from '../../../services/reservation.service';
import { environment } from '../../../../environments/environment.development';
import { iTrip } from '../../../interfaces/itrip';
import { TripService } from '../../../services/trip.service';
import { iReservationrequest } from '../../../interfaces/ireservationrequest';
import { iEmployee } from '../../../interfaces/iemployee';
import { EmployeeService } from '../../../services/employee.service';
import { IReservationresponse } from '../../../interfaces/ireservationresponse';
import { iAddpreference } from '../../../interfaces/iaddpreference';

@Component({
  selector: 'app-res-modal',
  templateUrl: './res-modal.component.html',
  styleUrl: './res-modal.component.scss',
})
export class ResModalComponent {
  constructor(
    public activeModal: NgbActiveModal,
    private reservationSvc: ReservationService,
    private tripSvc: TripService,
    private employeeSvc: EmployeeService
  ) {}

  @Input() employee!: iEmployee;
  @Input() reservation: IReservationresponse | Partial<iReservation> = {};
  @Input() isReservation!: boolean;
  @Input() trip!: iTrip;

  trips!: iTrip[];
  tripId: number = 0;

  employees: iEmployee[] = [];

  tripPreferences: string[] = environment.tripPreferences;

  @Input() employeeId: number = 0;

  newPreferences: Partial<iPreference>[] = [];

  ngOnInit() {
    if (this.reservation.id) {
      this.employeeId = this.reservation.employee!.id;
    }

    if (this.trip && !this.reservation.trip) {
      this.tripId = this.trip.id;
    } else if (this.reservation.trip) {
      this.tripId = this.reservation.trip!.id;
    }

    this.tripSvc.getTrips().subscribe((res) => (this.trips = res));
    this.employeeSvc.getEmployees().subscribe((res) => {
      this.employees = res;
    });
  }

  addPreference(event: Event) {
    let preference = (event.target as HTMLSelectElement).value;

    let newPreference: Partial<iPreference> = {
      type: preference,
      name: '',
      location: '',
    };
    this.newPreferences.push(newPreference);
  }

  addReservation() {
    let request: iReservationrequest = {
      tripId: +this.tripId,
      requestDate: new Date().toISOString().slice(0, 10),
      employeeId: +this.employeeId,
      preferences: this.newPreferences,
    };
    this.reservationSvc.addReservation(request).subscribe((res) => {
      setTimeout(() => {
        this.activeModal.close();
      }, 1000);
    });
  }

  close() {
    this.activeModal.close();
  }

  save() {
    let preference: iAddpreference = {
      reservationId: this.reservation.id!,
      type: this.newPreferences[0].type!,
      name: this.newPreferences[0].name!,
      location: this.newPreferences[0].location || '',
    };
    this.reservationSvc
      .addPreference(this.reservation.id!, preference)
      .subscribe();
  }
}
