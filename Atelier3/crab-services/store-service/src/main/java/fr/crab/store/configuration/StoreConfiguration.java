package fr.crab.store.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"fr.crab.entity"})
@ComponentScan(basePackages = {"fr.crab"})
public class StoreConfiguration {
}
