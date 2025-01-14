import { Component } from '@angular/core';
import { PagetitleService } from '../../services/pagetitle.service';
import { ResModalComponent } from '../../components/modal/res-modal/res-modal.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IReservationresponse } from '../../interfaces/ireservationresponse';
import { ReservationService } from '../../services/reservation.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrl: './reservation.component.scss',
})
export class ReservationComponent {
  constructor(
    private pageTitle: PagetitleService,
    private modalService: NgbModal,
    private reservationSvc: ReservationService
  ) {}

  reservations!: IReservationresponse[];

  ngOnInit() {
    this.pageTitle.title.next('Manage reservations');
    this.reservationSvc.reservations$.subscribe(
      (res) => (this.reservations = res)
    );
    this.reservationSvc.getReservations().subscribe();
  }

  openModal(isReservation: boolean) {
    const modalRef = this.modalService.open(ResModalComponent, {
      centered: true,
    });
    modalRef.componentInstance.isReservation = isReservation;
  }
}
