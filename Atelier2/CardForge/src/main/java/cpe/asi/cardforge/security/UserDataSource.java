package cpe.asi.cardforge.security;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

public class UserDataSource {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5432/card_game");
        driverManagerDataSource.setUsername("sa");
        driverManagerDataSource.setPassword("sa");
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");

        return driverManagerDataSource;
    }
}
