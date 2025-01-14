package epicode.it.businesstrips.entities.preference;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="preferences")
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PreferenceType type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;
}