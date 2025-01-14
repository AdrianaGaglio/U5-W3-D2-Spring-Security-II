package epicode.it.businesstrips.auth.dto;

import epicode.it.businesstrips.entities.employee.dto.EmployeeCreateRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    private String username;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    private String password;

    private EmployeeCreateRequest employeeRequest;

    public RegisterRequest() {}

    public RegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public RegisterRequest(String username, String password, EmployeeCreateRequest employeeRequest) {
        this.username = username;
        this.password = password;
        this.employeeRequest = employeeRequest;
    }
}
