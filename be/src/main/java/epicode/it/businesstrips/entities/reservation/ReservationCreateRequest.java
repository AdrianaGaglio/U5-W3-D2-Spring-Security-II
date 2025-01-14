package epicode.it.businesstrips.entities.reservation;

import epicode.it.businesstrips.entities.employee.Employee;
import epicode.it.businesstrips.entities.preference.Preference;
import epicode.it.businesstrips.entities.preference.PreferenceCreateRequest;
import epicode.it.businesstrips.entities.preference.PreferenceType;
import epicode.it.businesstrips.entities.trip.Trip;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
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
