package epicode.it.businesstrips.auth.appuser;

import epicode.it.businesstrips.entities.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT au FROM AppUser au " +
           "JOIN au.employee e " +
           "WHERE e.email = :email")
    AppUser findByEmployeeEmail(@Param("email") String email);
}
