package epicode.it.businesstrips.auth.appuser;

import epicode.it.businesstrips.auth.configurations.PwdEncoder;
import epicode.it.businesstrips.auth.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class AuthRunner implements ApplicationRunner {

    @Autowired
    private AppUserSvc appUserSvc;

    @Autowired
    private PwdEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Creazione dell'utente admin se non esiste
        Optional<AppUser> adminUser = appUserSvc.findByUsername("admin");
        if (adminUser.isEmpty()) {
            RegisterRequest request = new RegisterRequest("admin", "admin@mail.com", "adminpwd");
            appUserSvc.registerUser(request, Set.of(Role.ROLE_ADMIN));
        }

        // Creazione dell'utente user se non esiste
        Optional<AppUser> normalUser = appUserSvc.findByUsername("user");
        if (normalUser.isEmpty()) {
            RegisterRequest request = new RegisterRequest("user", "user@mail.com", "userpwd");
            appUserSvc.registerUser(request, Set.of(Role.ROLE_USER));
        }
    }
}
