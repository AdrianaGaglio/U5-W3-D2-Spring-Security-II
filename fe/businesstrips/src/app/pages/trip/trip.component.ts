import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TripService } from '../../services/trip.service';
import { iTrip } from '../../interfaces/itrip';
import { ReservationService } from '../../services/reservation.service';
import { IReservationresponse } from '../../interfaces/ireservationresponse';

@Component({
  selector: 'app-trip',
  templateUrl: './trip.component.html',
  styleUrl: './trip.component.scss',
})
export class TripComponent {
  constructor(
    private route: ActivatedRoute,
    private tripSvc: TripService,
    private reservationSvc: ReservationService
  ) {}

  trip!: iTrip;
  reservations!: IReservationresponse[];

  ngOnInit() {
    this.route.params.subscribe((params: any) => {
      let id = +params['id'];
      this.tripSvc.getById(id).subscribe((trip: iTrip) => {
        if (trip) {
          this.trip = trip;
          this.reservationSvc
            .getByTrip(trip.id)
            .subscribe((res) => (this.reservations = res));
        }
      });
    });
  }
}
