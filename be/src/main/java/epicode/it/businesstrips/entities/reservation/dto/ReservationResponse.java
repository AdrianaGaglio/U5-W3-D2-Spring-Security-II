package epicode.it.businesstrips.entities.reservation;

import epicode.it.businesstrips.entities.employee.Employee;
import epicode.it.businesstrips.entities.employee.EmployeeResponse;
import epicode.it.businesstrips.entities.preference.Preference;
import epicode.it.businesstrips.entities.trip.Trip;
import epicode.it.businesstrips.entities.trip.TripResponse;
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
