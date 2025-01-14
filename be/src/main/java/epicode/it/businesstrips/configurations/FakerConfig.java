package epicode.it.businesstrips.configurations;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class FakerConfig {

    @Bean
    public Faker getFaker() {
        return new Faker(new Locale("it"));
    }
}
