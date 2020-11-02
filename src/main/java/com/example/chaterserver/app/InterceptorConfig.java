package com.example.chaterserver.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class InterceptorConfig {

    @Bean
    public AuthorizationInterceptor authorizationInterceptor(){
        return new AuthorizationInterceptor();
    };

}
