package epicode.it.businesstrips.entities.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    public boolean existsByEmail(String email);

    public Employee findFirstByEmail(String email);

    @Query("SELECT e FROM Employee e JOIN e.appUser au WHERE au.username = :username")
    public Employee findFirstByUsername(@Param("username") String username);

    @Query("SELECT e FROM Employee e  WHERE e.firstName LIKE %:name% OR e.lastName LIKE %:name% OR e.appUser.username LIKE %:name%")
    public List<Employee> findByFirstNameOrLastNameOrUsername(String name);
}
