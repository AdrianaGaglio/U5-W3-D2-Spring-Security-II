package epicode.it.businesstrips.entities.trip;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TripUpdateRequest {

    @NotNull(message = "ID is required")
    private Long id;

    private String destination;

    @Future(message = "Date must be in the future")
    private LocalDate date;

    private int maxCapacity;
}
