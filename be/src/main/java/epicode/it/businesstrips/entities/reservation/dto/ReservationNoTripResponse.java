package epicode.it.businesstrips.entities.reservation.dto;

import epicode.it.businesstrips.entities.employee.dto.EmployeeResponse;
import epicode.it.businesstrips.entities.preference.Preference;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationNoTripResponse {
    private Long id;
    private EmployeeResponse employee;
    private LocalDate requestDate;
    private List<Preference> preferences;
}
