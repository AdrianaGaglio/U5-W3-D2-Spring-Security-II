import { Component, Input } from '@angular/core';
import { iReservation } from '../../../interfaces/ireservation';
import { ReservationService } from '../../../services/reservation.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IReservationresponse } from '../../../interfaces/ireservationresponse';
import { ResModalComponent } from '../../modal/res-modal/res-modal.component';
import { iTrip } from '../../../interfaces/itrip';
import { Router } from '@angular/router';

@Component({
  selector: 'app-res-card',
  templateUrl: './res-card.component.html',
  styleUrl: './res-card.component.scss',
})
export class ResCardComponent {
  constructor(
    private reservationSvc: ReservationService,
    private modalService: NgbModal,
    private router: Router
  ) {}

  @Input() reservation!: iReservation;
  @Input() trip!: iTrip;
  isTrip: boolean = false;

  ngOnInit() {
    if (this.router.url.startsWith('/trip')) {
      this.isTrip = true;
    }
  }

  openModal(reservation: IReservationresponse) {
    const modalRef = this.modalService.open(ResModalComponent, {
      centered: true,
    });
    modalRef.componentInstance.reservation = reservation;
  }

  openModalWithTrip(reservation: IReservationresponse, trip: iTrip) {
    const modalRef = this.modalService.open(ResModalComponent, {
      centered: true,
    });
    modalRef.componentInstance.reservation = reservation;
    modalRef.componentInstance.trip = trip;
  }

  delete(reservation: iReservation) {
    this.reservationSvc.delete(reservation.id).subscribe();
  }
}
