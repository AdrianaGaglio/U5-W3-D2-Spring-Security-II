package epicode.it.businesstrips.entities.employee;

import epicode.it.businesstrips.auth.appuser.AppUser;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class EmployeeResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String image;

    private String username;
}
