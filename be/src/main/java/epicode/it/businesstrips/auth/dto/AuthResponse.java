package epicode.it.businesstrips.auth.dto;

import epicode.it.businesstrips.entities.employee.dto.EmployeeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AuthResponse {
    private String token;
    private EmployeeResponse user;

    public AuthResponse(String token, EmployeeResponse user) {
        this.token = token;
        this.user = user;
    }

    public AuthResponse(String token) {
        this.token = token;
    }
}
