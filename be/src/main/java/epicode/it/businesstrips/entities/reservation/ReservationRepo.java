package epicode.it.businesstrips.entities.reservation;

import epicode.it.businesstrips.entities.employee.Employee;
import epicode.it.businesstrips.entities.trip.Trip;
import org.hibernate.annotations.Parent;
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

    @Query("SELECT r FROM Reservation r WHERE r.requestDate = :date")
    public List<Reservation> findAllByDate(@Param("date") LocalDate date);

    @Query("SELECT r FROM Reservation r WHERE LOWER(r.trip.destination) LIKE CONCAT(LOWER(:destination), '%')")
    public List<Reservation> findAllByDestination(@Param("destination") String destination);

    @Query("SELECT r FROM Reservation r WHERE LOWER(r.trip.destination) LIKE CONCAT(LOWER(:destination), '%') AND r.requestDate = :date")
    List<Reservation> findAllByDestinationAndDate(@Param("destination") String destination, @Param("date") LocalDate date);

}
