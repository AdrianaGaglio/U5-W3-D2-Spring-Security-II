package epicode.it.businesstrips.entities.trip;

import jakarta.persistence.*;
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
