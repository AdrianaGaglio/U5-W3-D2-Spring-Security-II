package epicode.it.businesstrips.entities.trip;

import epicode.it.businesstrips.entities.reservation.ReservationNoTripResponse;
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
