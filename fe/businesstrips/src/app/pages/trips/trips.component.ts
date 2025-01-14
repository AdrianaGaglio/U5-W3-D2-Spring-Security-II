import { Component } from '@angular/core';
import { PagetitleService } from '../../services/pagetitle.service';
import { iTrip } from '../../interfaces/itrip';
import { TripService } from '../../services/trip.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from '../../components/modal/modal.component';

@Component({
  selector: 'app-trips',
  templateUrl: './trips.component.html',
  styleUrl: './trips.component.scss',
})
export class TripsComponent {
  constructor(
    private pageTitle: PagetitleService,
    private tripSvc: TripService,
    private modalService: NgbModal
  ) {}

  trips!: iTrip[];
  destination: string = '';

  ngOnInit() {
    this.pageTitle.title.next('Manage trips');
    this.tripSvc.trips$.subscribe((trips) => {
      this.trips = trips;
    });
    this.tripSvc.getTrips().subscribe();
  }

  openModal(isTrip: boolean) {
    const modalRef = this.modalService.open(ModalComponent, { centered: true });
    modalRef.componentInstance.isTrip = isTrip;
  }

  search(destination: string) {
    this.tripSvc.search(destination).subscribe((trips) => (this.trips = trips));
  }
}
