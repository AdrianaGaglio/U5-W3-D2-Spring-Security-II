package epicode.it.businesstrips.entities.reservation.dto;

import epicode.it.businesstrips.entities.employee.dto.EmployeeResponse;
import epicode.it.businesstrips.entities.preference.Preference;
import epicode.it.businesstrips.entities.trip.dto.TripResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationResponse {
    private Long id;
    private EmployeeResponse employee;
    private LocalDate requestDate;
    private List<Preference> preferences;
    private TripResponse trip;
}
