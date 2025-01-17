package epicode.it.businesstrips.entities.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.appUser.email = :email")
    public Employee findFirstByEmail(String email);

    @Query("SELECT e FROM Employee e JOIN e.appUser au WHERE au.username = :username")
    public Employee findFirstByUsername(@Param("username") String username);

    @Query("SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE CONCAT(:name, '%') OR LOWER(e.lastName) LIKE CONCAT(:name, '%') OR LOWER(e.appUser.username) LIKE CONCAT(:name, '%')")
    public List<Employee> findFirstByFirstNameOrLastNameOrUsername(@Param("name") String name);

    @Query("SELECT e FROM Employee e WHERE e.appUser.id = :id")
    public Employee findByAppUserId(@Param("id") Long id);

}
