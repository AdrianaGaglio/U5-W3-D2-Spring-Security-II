package epicode.it.businesstrips.entities.employee.dto;

import epicode.it.businesstrips.entities.employee.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeResponseMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public EmployeeResponse toEmployeeResponse(Employee e) {
        EmployeeResponse response = modelMapper.map(e, EmployeeResponse.class);
        response.setUsername(e.getAppUser().getUsername());
        response.setEmail(e.getAppUser().getEmail());
        return response;
    }

    public List<EmployeeResponse> toEmployeeResponseList(List<Employee> employees) {
        return employees.stream().map(this::toEmployeeResponse).toList();
    }
}
