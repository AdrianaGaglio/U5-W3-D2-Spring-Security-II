package epicode.it.businesstrips.auth.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginRequest {
    private String username;

    @Email(message = "Invalid email address")
    private String email;
    private String password;
}
