package epicode.it.businesstrips.controllers;

import epicode.it.businesstrips.entities.employee.*;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeSvc employeeSvc;

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAll() {
        return ResponseEntity.ok(employeeSvc.getAll());
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<EmployeeResponse>> getAllPageable(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(employeeSvc.getAllPageable(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeSvc.getByIdResponse(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return new ResponseEntity<>(employeeSvc.delete(id), HttpStatus.NO_CONTENT);
    }

    @PostMapping
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

}