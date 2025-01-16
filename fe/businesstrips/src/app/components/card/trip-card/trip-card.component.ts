import { Component, inject, Input, TemplateRef } from '@angular/core';
import { iEmployee } from '../../../interfaces/iemployee';
import { EmployeeService } from '../../../services/employee.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from '../../modal/modal.component';
import { iTrip } from '../../../interfaces/itrip';
import { TripService } from '../../../services/trip.service';
import { environment } from '../../../../environments/environment.development';
import { AuthService } from '../../../auth/auth.service';

@Component({
  selector: 'app-trip-card',
  templateUrl: './trip-card.component.html',
  styleUrl: './trip-card.component.scss',
})
export class TripCardComponent {
  constructor(
    private tripSvc: TripService,
    private modalService: NgbModal,
    private authSvc: AuthService
  ) {}
  message!: string;
  editStatus: boolean = false;
  tripStatus: string[] = environment.tripStatus;

  @Input() trip!: iTrip;
  isAdmin: boolean = false;

  ngOnInit() {
    if (this.authSvc.decodeRole() === 'ADMIN') {
      this.isAdmin = true;
    }
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
