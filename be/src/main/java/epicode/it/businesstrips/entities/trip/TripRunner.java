package epicode.it.businesstrips.entities.trip;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class TripRunner implements ApplicationRunner {
    private final TripSvc tripSvc;
    private final Faker faker;
    private final Logger logger;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<String> destinations = List.of("Paris", "Berlin", "Madrid", "London", "Bruxelles", "Amsterdam", "Lisbon", "Vienna");

        if (tripSvc.count() == 0) {

            for (int i = 0; i < 20; i++) {

                TripCreateRequest newTrip = new TripCreateRequest();

                newTrip.setDestination(destinations.get(faker.number().numberBetween(0, destinations.size() - 1)));
                newTrip.setDate(faker.date().future(365, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                newTrip.setMaxCapacity(faker.number().numberBetween(1, 10));

                try {
                    tripSvc.create(newTrip);

                } catch (RuntimeException e) {
                    System.out.println(newTrip);
                    logger.error(e.getMessage());
                }
            }
        }

    }
}
