package epicode.it.businesstrips.entities.reservation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import epicode.it.businesstrips.entities.employee.Employee;
import epicode.it.businesstrips.entities.employee.EmployeeSvc;
import epicode.it.businesstrips.entities.preference.Preference;
import epicode.it.businesstrips.entities.trip.Trip;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonBackReference
    private Trip trip;

    @Column(name="request_date" , nullable = false)
    private LocalDate requestDate;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "reservation_id")
    private List<Preference> preferences = new ArrayList<>();
}