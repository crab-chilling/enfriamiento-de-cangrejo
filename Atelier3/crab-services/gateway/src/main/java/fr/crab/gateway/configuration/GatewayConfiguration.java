package fr.crab.gateway.configuration;

import fr.crab.security.WebSecurityConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"fr.crab.entity"})
@EnableJpaRepositories(basePackages = {"fr.crab.repository"})
@ComponentScan(basePackages = {"fr.crab"})
@Import(WebSecurityConfig.class)
public class GatewayConfiguration {
}
