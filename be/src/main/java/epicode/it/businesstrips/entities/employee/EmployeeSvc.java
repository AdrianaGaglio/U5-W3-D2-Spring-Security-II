package epicode.it.businesstrips.entities.employee;

import epicode.it.businesstrips.auth.appuser.AppUser;
import epicode.it.businesstrips.auth.appuser.AppUserRepo;
import epicode.it.businesstrips.entities.employee.dto.EmployeeCreateRequest;
import epicode.it.businesstrips.entities.employee.dto.EmployeeResponse;
import epicode.it.businesstrips.entities.employee.dto.EmployeeResponseMapper;
import epicode.it.businesstrips.entities.employee.dto.EmployeeUpdateRequest;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
    private final EmployeeResponseMapper mapper;
    private final AppUserRepo appUserRepo;

    public List<EmployeeResponse> getAll() {
        List<EmployeeResponse> response = mapper.toEmployeeResponseList(employeeRepo.findAll());
        return response;
    }

    public Page<EmployeeResponse> getAllPageable(Pageable pageable) {
        Page<Employee> pagedEmployees = employeeRepo.findAll(pageable);
        Page<EmployeeResponse> response = pagedEmployees.map(e -> {
            EmployeeResponse employeeResponse = mapper.toEmployeeResponse(e);
            return employeeResponse;
        });
        return response;
    }

    public Employee getById(Long id) {
        return employeeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    public EmployeeResponse getByIdResponse(Long id) {
        EmployeeResponse response = mapper.toEmployeeResponse(getById(id));
        return response;
    }

    public int count() {
        return (int) employeeRepo.count();
    }

    @Transactional
    public String delete(Long id) {
        Employee e = getById(id);
        AppUser au = appUserRepo.findById(e.getAppUser().getId()).orElse(null);
        appUserRepo.delete(au);
        employeeRepo.delete(e);
        return "Employee deleted successfully";
    }

    public String delete(Employee e) {
        Employee foundEmployee = getById(e.getId());
        employeeRepo.delete(foundEmployee);
        return "Employee deleted successfully";
    }

    public EmployeeResponse create(@Valid EmployeeCreateRequest request) {

        Employee e = new Employee();
        e.setFirstName(request.getFirstName());
        e.setLastName(request.getLastName());
        e.setImage(request.getImage() != null || !request.getImage().isEmpty() ? request.getImage() : "https://ui-avatars.com/api/?name=" + e.getFirstName() + "+" + e.getLastName());

        if (request.getUserId() != null) {
            e.setAppUser(appUserRepo.findById(request.getUserId()).orElse(null));
        }
        e = employeeRepo.save(e);
        EmployeeResponse response = mapper.toEmployeeResponse(e);
        return response;
    }

    public EmployeeResponse update(Long id, @Valid EmployeeUpdateRequest request) {
        Employee e = getById(id);

        Employee foundE = employeeRepo.findFirstByEmail(request.getEmail().toLowerCase());
        boolean emailOk = foundE == null || !foundE.getId().equals(request.getId());
        if (request.getEmail() != null && foundE != null && emailOk)
            throw new EntityExistsException("Email already exists");


        Employee foundEn = employeeRepo.findFirstByUsername(request.getUsername().toLowerCase());
        boolean usernameOk = foundEn == null || !foundEn.getId().equals(request.getId());
        if (request.getUsername() != null && employeeRepo.findFirstByUsername(request.getUsername().toLowerCase()) != null && usernameOk)
            throw new EntityExistsException("Username already exists");


        e.setFirstName(request.getFirstName() != null ? request.getFirstName() : e.getFirstName());
        e.setLastName(request.getLastName() != null ? request.getLastName() : e.getLastName());

        e.setImage(request.getImage() != null ? request.getImage() : e.getImage());

        e = employeeRepo.save(e);

        EmployeeResponse response = mapper.toEmployeeResponse(e);

        return response;
    }

    public List<EmployeeResponse> findByFirstNameOrLastNameOrUsername(String name) {
        List<Employee> employees = employeeRepo.findFirstByFirstNameOrLastNameOrUsername(name.toLowerCase());
        List<EmployeeResponse> response = mapper.toEmployeeResponseList(employeeRepo.findFirstByFirstNameOrLastNameOrUsername(name.toLowerCase()));

        return response;
    }

    public Employee findByAppUserId(Long id) {
        return employeeRepo.findByAppUserId(id);
    }
}
