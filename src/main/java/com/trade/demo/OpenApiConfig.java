package com.trade.demo;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI failoverDemoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Failover Demo API")
                        .description("Deal and Client management with client-service availability failover")
                        .version("v1"));
    }
}