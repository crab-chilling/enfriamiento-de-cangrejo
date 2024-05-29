package fr.crab.store.configuration;

import fr.crab.security.WebSecurityConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EntityScan(basePackages = {"fr.crab.entity"})
@ComponentScan(basePackages = {"fr.crab"})
@Import(value = {WebSecurityConfig.class})
public class StoreConfiguration {
}
