package epicode.it.businesstrips.entities.reservation;

import epicode.it.businesstrips.entities.employee.Employee;
import epicode.it.businesstrips.entities.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {


    List<Reservation> findFirstByTrip(Trip trip);

    public Reservation findFirstByTripAndEmployee(Trip trip, Employee employee);

   List<Reservation> findByEmployeeOrderByRequestDateDesc(Employee employee);


    public Reservation findFirstById(Long id);


}
