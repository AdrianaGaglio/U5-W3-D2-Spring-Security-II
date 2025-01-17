package epicode.it.businesstrips.auth.appuser;

import epicode.it.businesstrips.entities.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmail(String email);


    boolean existsByUsername(String username);
    boolean existsByEmail(String email);


    @Query("SELECT u FROM AppUser u WHERE LOWER(u.username) = LOWER(:usernameOrEmail) OR LOWER(u.email) = LOWER(:usernameOrEmail)")
    public AppUser findFirstByUsernameOrEmail(String usernameOrEmail);
}
