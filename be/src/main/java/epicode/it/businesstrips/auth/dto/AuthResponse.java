package epicode.it.businesstrips.auth.dto;

import epicode.it.businesstrips.entities.employee.dto.EmployeeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private EmployeeResponse user;
}
