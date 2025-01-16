import { Component, inject, Input, TemplateRef } from '@angular/core';
import { iEmployee } from '../../../interfaces/iemployee';
import { EmployeeService } from '../../../services/employee.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from '../../modal/modal.component';
import { iTrip } from '../../../interfaces/itrip';
import { TripService } from '../../../services/trip.service';
import { environment } from '../../../../environments/environment.development';
import { ResModalComponent } from '../../modal/res-modal/res-modal.component';
import { AuthService } from '../../../auth/auth.service';

@Component({
  selector: 'app-trip-hor-card',
  templateUrl: './trip-hor-card.component.html',
  styleUrl: './trip-hor-card.component.scss',
})
export class TripHorCardComponent {
  constructor(
    private tripSvc: TripService,
    private modalService: NgbModal,
    private authSvc: AuthService
  ) {}
  message!: string;
  editStatus: boolean = false;
  tripStatus: string[] = environment.tripStatus;
  isAdmin: boolean = false;
  employeeId!: number;

  @Input() trip!: iTrip;

  ngOnInit() {
    const json = localStorage.getItem('authData');
    if (json) {
      this.employeeId = JSON.parse(json).user.id;
    }
    this.authSvc.isAdmin$.subscribe((res) => (this.isAdmin = res));
  }

  delete(trip: iTrip) {
    this.tripSvc
      .delete(trip)
      .subscribe((res) => (this.message = 'Trip deleted successfully!'));
  }

  openModal(trip: iTrip) {
    const modalRef = this.modalService.open(ModalComponent, { centered: true });
    modalRef.componentInstance.trip = trip;
  }

  openModalRes(trip: iTrip) {
    const modalRef = this.modalService.open(ResModalComponent, {
      centered: true,
    });
    modalRef.componentInstance.trip = trip;
    modalRef.componentInstance.employeeId = this.employeeId;
  }

  enableChange() {
    this.editStatus = true;
  }

  changeStatus(event: Event) {
    let status = (event.target as HTMLSelectElement).value;
    this.tripSvc.changeStatus(this.trip.id, status).subscribe((res) => {
      this.trip = res;
      this.editStatus = false;
    });
  }
}
