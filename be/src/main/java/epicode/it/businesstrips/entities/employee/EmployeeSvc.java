package epicode.it.businesstrips.entities.employee;

import epicode.it.businesstrips.auth.appuser.AppUserRepo;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class EmployeeSvc {
    private final EmployeeRepo employeeRepo;
    private final AppUserRepo appUserRepo;

    public List<EmployeeResponse> getAll() {
        List<Employee> employees = employeeRepo.findAll();
        List<EmployeeResponse> response = new ArrayList<>();
        for (Employee e : employees) {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            BeanUtils.copyProperties(e, employeeResponse);
            employeeResponse.setUsername(e.getAppUser().getUsername());
            response.add(employeeResponse);
        }
        return response;
    }

    public Page<EmployeeResponse> getAllPageable(Pageable pageable) {
        Page<Employee> pagedEmployees = employeeRepo.findAll(pageable);
        Page<EmployeeResponse> response = pagedEmployees.map(e -> {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            BeanUtils.copyProperties(e, employeeResponse);
            employeeResponse.setUsername(e.getAppUser().getUsername());
            return employeeResponse;
        });
        return response;
    }

    public Employee getById(Long id) {
        return employeeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    public EmployeeResponse getByIdResponse(Long id) {
        Employee e = getById(id);
        EmployeeResponse response = new EmployeeResponse();
        BeanUtils.copyProperties(e, response);
        response.setUsername(e.getAppUser().getUsername());
        return response;
    }

    public int count() {
        return (int) employeeRepo.count();
    }

    public String delete(Long id) {
        Employee e = getById(id);
        employeeRepo.delete(e);
        return "Employee deleted successfully";
    }

    public String delete(Employee e) {
        Employee foundEmployee = getById(e.getId());
        employeeRepo.delete(foundEmployee);
        return "Employee deleted successfully";
    }

    public EmployeeResponse create(@Valid EmployeeCreateRequest request) {
        if (employeeRepo.existsByEmail(request.getEmail().toLowerCase())) {
            throw new EntityExistsException("Email already exists");
        }
        Employee e = new Employee();
        e.setFirstName(request.getFirstName());
        e.setLastName(request.getLastName());
        e.setImage(request.getImage());
        e.setEmail(request.getEmail().toLowerCase());
        if (request.getUserId() != null) {
            e.setAppUser(appUserRepo.findById(request.getUserId()).orElse(null));
        }
        e = employeeRepo.save(e);
        EmployeeResponse response = new EmployeeResponse();
        BeanUtils.copyProperties(e, response);
        response.setUsername(e.getAppUser().getUsername());
        return response;
    }

    public EmployeeResponse update(Long id, @Valid EmployeeUpdateRequest request) {
        Employee e = getById(id);

        Employee foundE = employeeRepo.findFirstByEmail(request.getEmail().toLowerCase());
        boolean emailOk = foundE == null || !foundE.getId().equals(request.getId());
        if (request.getEmail() != null && employeeRepo.existsByEmail(request.getEmail().toLowerCase()) && emailOk)
            throw new EntityExistsException("Email already exists");
        e.setEmail(request.getEmail() != null ? request.getEmail().toLowerCase() : e.getEmail());

        Employee foundEn = employeeRepo.findFirstByUsername(request.getUsername().toLowerCase());
        boolean usernameOk = foundEn == null || !foundEn.getId().equals(request.getId());
        if (request.getUsername() != null && employeeRepo.findFirstByUsername(request.getUsername().toLowerCase()) != null && usernameOk)
            throw new EntityExistsException("Username already exists");


        e.setFirstName(request.getFirstName() != null ? request.getFirstName() : e.getFirstName());
        e.setLastName(request.getLastName() != null ? request.getLastName() : e.getLastName());

        e.setImage(request.getImage() != null ? request.getImage() : e.getImage());

        e = employeeRepo.save(e);

        EmployeeResponse response = new EmployeeResponse();
        BeanUtils.copyProperties(e, response);
        response.setUsername(e.getAppUser().getUsername());
        return response;
    }

    public List<EmployeeResponse> findByFirstNameOrLastNameOrUsername(String name) {
        List<Employee> employees = employeeRepo.findByFirstNameOrLastNameOrUsername(name);
        List<EmployeeResponse> response = new ArrayList<>();
        for (Employee e : employees) {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            BeanUtils.copyProperties(e, employeeResponse);
            employeeResponse.setUsername(e.getAppUser().getUsername());
            response.add(employeeResponse);
        }
        return response;
    }
}