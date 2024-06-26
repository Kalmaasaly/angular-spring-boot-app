package org.serp.booklending;

import org.serp.booklending.model.Role;
import org.serp.booklending.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class BookLendingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookLendingApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(RoleRepository repository){
        return args->{
            if (repository.findByName("USER").isEmpty()) {
                
                repository.save(
                    Role
                    .builder()
                    .name("USER")
                    .build());
            }
        };

    }
}
