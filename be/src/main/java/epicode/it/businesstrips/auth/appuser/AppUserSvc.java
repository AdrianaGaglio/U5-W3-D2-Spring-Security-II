package epicode.it.businesstrips.auth.appuser;

import epicode.it.businesstrips.auth.configurations.PwdEncoder;
import epicode.it.businesstrips.auth.dto.AuthResponse;
import epicode.it.businesstrips.auth.dto.LoginRequest;
import epicode.it.businesstrips.auth.dto.RegisterRequest;
import epicode.it.businesstrips.auth.jwt.JwtTokenUtil;
import epicode.it.businesstrips.entities.employee.Employee;
import epicode.it.businesstrips.entities.employee.dto.EmployeeCreateRequest;
import epicode.it.businesstrips.entities.employee.EmployeeSvc;
import epicode.it.businesstrips.entities.employee.dto.EmployeeResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor // Genera automaticamente un costruttore con tutti i campi final, riducendo il boilerplate
@Validated
public class AppUserSvc {

    private final AppUserRepo appUserRepo; // Repository per gestire le operazioni di persistenza per AppUser
    private final PwdEncoder encoder; // Utilità per codificare le password
    private final AuthenticationManager authenticationManager; // Gestisce il processo di autenticazione
    private final JwtTokenUtil jwtTokenUtil; // Utilità per generare e gestire token JWT

    private final EmployeeSvc employeeSvc;

    //    /**
//     * Registra un nuovo utente nel sistema.
//     * @param username Il nome utente dell'utente.
//     * @param password La password dell'utente.
//     * @param roles I ruoli assegnati all'utente.
//     * @return L'entità salvata AppUser.
//     */
    public AppUser registerUser(@Valid RegisterRequest request, Set<Role> roles) {
        // Controlla se l'username esiste già nel database.
        if (appUserRepo.existsByUsername(request.getUsername())) {
            throw new EntityExistsException("Username già in uso"); // Lancia un'eccezione se l'username è già in uso
        }

        if (request.getEmployeeRequest() != null && findByEmail(request.getEmployeeRequest().getEmail()) != null)
            throw new EntityExistsException("Email already exists");

        // Crea una nuova istanza di AppUser e imposta i suoi campi.
        AppUser appUser = new AppUser();
        appUser.setUsername(request.getUsername());
        // Codifica la password utilizzando il PasswordEncoder.
        appUser.setPassword(encoder.passwordEncoder().encode(request.getPassword()));
        appUser.setRoles(roles); // Imposta i ruoli per l'utente.

        // Salva l'utente nel database e restituisce l'oggetto salvato.
        appUser = appUserRepo.save(appUser);


        if (request.getEmployeeRequest() != null) {
            EmployeeCreateRequest requestEmployee = new EmployeeCreateRequest();
            BeanUtils.copyProperties(request.getEmployeeRequest(), requestEmployee);
            requestEmployee.setUserId(appUser.getId());
            employeeSvc.create(requestEmployee);
        }
        return appUser;

    }

    //    /**
//     * Trova un utente in base all'username.
//     * @param username Il nome utente da cercare.
//     * @return Un Optional che contiene l'utente, se trovato.
//     */
    public Optional<AppUser> findByUsername(String username) {
        // Cerca l'utente nel database in base all'username.
        return appUserRepo.findByUsername(username);
    }

    public AppUser findByEmail(String email) {
        return appUserRepo.findByEmployeeEmail(email);
    }

    //    /**
//     * Autentica un utente e genera un token JWT se l'autenticazione ha successo.
//     * @param username Il nome utente.
//     * @param password La password.
//     * @return Un token JWT valido.
//     */
    public AuthResponse authenticateUser(@Valid LoginRequest request) {
        try {

            if (request.getEmail() != null) {
                AppUser found = findByEmail(request.getEmail());
                if (found == null) throw new EntityNotFoundException("User not found");
                request.setUsername(found.getUsername());
            }

            if (request.getUsername() != null && findByUsername(request.getUsername()) == null)
                throw new EntityNotFoundException("User not found");

            // Crea un token di autenticazione e prova ad autenticare l'utente.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            EmployeeResponse user = new EmployeeResponse();

            if ((!request.getUsername().equals("admin") || !request.getUsername().equals("user")) && request.getEmail() != null) {
                String text = request.getUsername() != null ? request.getUsername() : request.getEmail();
                user = employeeSvc.findByFirstNameOrLastNameOrUsername(text).getFirst();
            }


            // Recupera i dettagli dell'utente autenticato.
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // Genera un token JWT per l'utente autenticato.
            if ((!request.getUsername().equals("admin") || !request.getUsername().equals("user")) && request.getEmail() != null) {
                return new AuthResponse(jwtTokenUtil.generateToken(userDetails), user);
            } else {
                return new AuthResponse(jwtTokenUtil.generateToken(userDetails));
            }

        } catch (AuthenticationException e) {
            // Lancia un'eccezione di sicurezza se l'autenticazione fallisce.
            throw new SecurityException("Credenziali non valide", e);
        }
    }

    //    /**
//     * Carica un utente dal database utilizzando l'username.
//     * @param username Il nome utente da cercare.
//     * @return L'entità AppUser corrispondente.
//     */
    public AppUser loadUserByUsername(String username) {
        // Cerca l'utente nel database, lanciando un'eccezione se non viene trovato.
        AppUser appUser = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con username: " + username));

        // Restituisce l'utente trovato.
        return appUser;
    }
}
