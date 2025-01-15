package epicode.it.businesstrips.entities.reservation.dto;

import epicode.it.businesstrips.entities.preference.dto.PreferenceCreateRequest;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationCreateRequest {

    @NotNull(message = "Trip ID is required")
    private Long tripId;

    @FutureOrPresent(message = "Request date cannot be in the past")
    private LocalDate requestDate;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    private List<PreferenceCreateRequest> preferences;

}