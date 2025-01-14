package epicode.it.businesstrips.entities.trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TripRepo extends JpaRepository<Trip, Long> {

    public boolean existsByDestinationAndDate(String destination, LocalDate date);

    @Query("SELECT t FROM Trip t WHERE LOWER(t.destination) LIKE CONCAT(:destination, '%')")
    public List<Trip> findByDestination(String destination);



}
