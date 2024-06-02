package fr.crab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Base64;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl(System.getProperty("conf.database.url") + System.getProperty("conf.database.dbName"));
        driverManagerDataSource.setUsername(System.getProperty("conf.database.username"));
        driverManagerDataSource.setPassword(System.getProperty("conf.database.password"));
        return driverManagerDataSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .csrf().disable().authorizeHttpRequests(nonAuthenticated ->
                            nonAuthenticated
                                    .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/register").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/card/generate/**").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/user/register").permitAll())
                    .authorizeHttpRequests(authenticate -> authenticate.anyRequest().authenticated())
                    .oauth2ResourceServer().jwt().decoder(jwtDecoder(jwtSecretKey() ));

        return httpSecurity.build();
    }

    @Bean
    public SecretKey jwtSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SecurityConstants.SECRET);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
    }
    @Bean
    public JwtDecoder jwtDecoder(SecretKey secretKey) {
       return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(MyUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);

        return providerManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
