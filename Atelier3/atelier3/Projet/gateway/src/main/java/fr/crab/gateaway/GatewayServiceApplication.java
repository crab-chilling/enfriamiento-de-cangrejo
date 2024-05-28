package fr.crab.chilling.edge;

import fr.crab.chilling.edge.config.RibbonConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@RibbonClients(defaultConfiguration = RibbonConfig.class)
public class GatewayServiceApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(GatewayServiceApplication.class).web(true).run(args);
    }
}
