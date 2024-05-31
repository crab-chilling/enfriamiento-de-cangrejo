package fr.crab.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {
    @Bean
    RouteLocator gateway(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("api-auth", routeSpec -> routeSpec
                        .path("/login")
                        .uri("lb://api-authentication-service"))
                .route("api-auth", routeSpec -> routeSpec
                        .path("/register")
                        .uri("lb://api-authentication-service"))
                .route("api-users", routeSpec -> routeSpec
                        .path("/user/**")
                        .uri("lb://api-user-service"))
                .route("api-cards", routeSpec -> routeSpec
                        .path("/card/**")
                        .uri("lb://api-card-service"))
                .route("api-store", routeSpec -> routeSpec
                        .path("/store-item/**")
                        .uri("lb://api-store-service"))
                .build();
    }
}
