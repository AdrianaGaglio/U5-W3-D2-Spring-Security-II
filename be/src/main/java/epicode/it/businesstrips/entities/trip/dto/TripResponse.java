package epicode.it.businesstrips.entities.trip.dto;

import epicode.it.businesstrips.entities.trip.TripStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TripResponse {
    private Long id;

    private String destination;

    private LocalDate date;

    private TripStatus status;

    private int maxCapacity;
}
