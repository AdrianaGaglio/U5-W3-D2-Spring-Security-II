package epicode.it.businesstrips.configurations;

import epicode.it.businesstrips.BusinesstripsApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {

    @Bean
    public Logger getLogger() {
        return LoggerFactory.getLogger(BusinesstripsApplication.class);
    }
}
