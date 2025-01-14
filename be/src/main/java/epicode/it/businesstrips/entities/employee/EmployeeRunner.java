package epicode.it.businesstrips.entities.employee;

import com.github.javafaker.Faker;
import epicode.it.businesstrips.auth.appuser.AppUserSvc;
import epicode.it.businesstrips.auth.appuser.Role;
import epicode.it.businesstrips.auth.dto.RegisterRequest;
import epicode.it.businesstrips.entities.employee.dto.EmployeeCreateRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class EmployeeRunner implements ApplicationRunner {
    private final EmployeeSvc employeeSvc;
    private final Faker faker;
    private final Logger logger;
    private final AppUserSvc appUserSvc;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (employeeSvc.count() == 0) {

            for (int i = 0; i < 100; i++) {

                EmployeeCreateRequest newEmployee = new EmployeeCreateRequest();
                newEmployee.setFirstName(faker.name().firstName());
                newEmployee.setLastName(faker.name().lastName());

                String lastName = newEmployee.getLastName();
                if (lastName.contains(" ") || lastName.contains("'")) {
                    lastName = lastName.replace(" ", "");
                    lastName = lastName.replace("'", "");
                }

                newEmployee.setEmail(newEmployee.getFirstName().toLowerCase() + "." + lastName.toLowerCase() + "@mail.com");
                newEmployee.setImage("https://ui-avatars.com/api/?name=" + newEmployee.getFirstName() + "+" + newEmployee.getLastName());

                RegisterRequest request = new RegisterRequest(newEmployee.getFirstName().toLowerCase() + lastName.toLowerCase().charAt(0), "password", newEmployee);

                try {
//                    employeeSvc.create(newEmployee);
                    appUserSvc.registerUser(request, Set.of(Role.ROLE_USER));
                } catch (RuntimeException e) {
                    System.out.println("===> " + newEmployee);
                    logger.error(e.getMessage());
                }
            }

        }
    }
}
