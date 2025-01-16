package epicode.it.businesstrips.entities.employee;

import epicode.it.businesstrips.auth.appuser.AppUser;
import epicode.it.businesstrips.entities.trip.Trip;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="first_name" , nullable = false)
    private String firstName;

    @Column(name="last_name" , nullable = false)
    private String lastName;

    private String image;

    @OneToOne
    @JoinColumn(name = "user_id",  unique = true)
    private AppUser appUser;
}