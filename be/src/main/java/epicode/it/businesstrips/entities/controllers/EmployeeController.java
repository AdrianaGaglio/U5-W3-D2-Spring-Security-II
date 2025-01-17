package epicode.it.businesstrips.entities.controllers;

import epicode.it.businesstrips.entities.employee.*;
import epicode.it.businesstrips.entities.employee.dto.EmployeeCreateRequest;
import epicode.it.businesstrips.entities.employee.dto.EmployeeResponse;
import epicode.it.businesstrips.entities.employee.dto.EmployeeUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class EmployeeController {
    private final EmployeeSvc employeeSvc;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmployeeResponse>> getAll() {
        return ResponseEntity.ok(employeeSvc.getAll());
    }

    @GetMapping("/paged")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<EmployeeResponse>> getAllPageable(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(employeeSvc.getAllPageable(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or isAuthenticated()")
    public ResponseEntity<EmployeeResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeSvc.getByIdResponse(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return new ResponseEntity<>(employeeSvc.delete(id), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponse> create(@RequestBody EmployeeCreateRequest request) {
        return new ResponseEntity<>(employeeSvc.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable Long id, @RequestBody EmployeeUpdateRequest
            request) {
        return new ResponseEntity<>(employeeSvc.update(id, request), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponse>> findByFirstNameOrLastNameOrUsername(@RequestParam String name) {
        return new ResponseEntity<>(employeeSvc.findByFirstNameOrLastNameOrUsername(name), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> count() {
        return ResponseEntity.ok(employeeSvc.count());
    }

}