package epicode.it.businesstrips.entities.trip;

import epicode.it.businesstrips.entities.employee.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TripCreateRequest {

    @NotNull(message = "Destination is required")
    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Date is required")
    @Future(message = "Date must be in the future")
    private LocalDate date;

    @NotNull(message = "Max capacity is required")
    @Min(value = 1, message = "Max capacity must be greater than 0")
    private int maxCapacity;
}
