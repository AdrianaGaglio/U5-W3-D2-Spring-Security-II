package epicode.it.businesstrips.auth.dto;

import epicode.it.businesstrips.entities.employee.dto.EmployeeCreateRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    private String username;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    private String password;

    private EmployeeCreateRequest employeeRequest;

    public RegisterRequest() {}

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public RegisterRequest(String username, String email,String password, EmployeeCreateRequest employeeRequest) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.employeeRequest = employeeRequest;
    }
}
