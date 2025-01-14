package epicode.it.businesstrips.entities.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeUpdateRequest {

    @NotNull(message = "ID is required")
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    @Email(message = "Invalid email address")
    private String email;

    private String image;
}
