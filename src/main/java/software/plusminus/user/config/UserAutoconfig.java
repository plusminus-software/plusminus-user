package software.plusminus.user.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("software.plusminus.user")
@EntityScan("software.plusminus.user")
@EnableJpaRepositories("software.plusminus.user")
public class UserAutoconfig {
}
