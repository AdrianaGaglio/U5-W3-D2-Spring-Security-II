package epicode.it.businesstrips.entities.trip.dto;

import epicode.it.businesstrips.entities.reservation.dto.ReservationNoTripResponse;
import epicode.it.businesstrips.entities.trip.TripStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class TripWithReservationResponse  {
    private Long id;

    private String destination;

    private LocalDate date;

    private TripStatus status;

    private int maxCapacity;

    private List<ReservationNoTripResponse> reservations = new ArrayList<>();
}
