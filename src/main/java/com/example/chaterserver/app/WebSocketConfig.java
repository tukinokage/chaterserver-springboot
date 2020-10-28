package com.example.chaterserver.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.swing.*;
import javax.websocket.Endpoint;

@Configuration
public class WebSocketConfig  {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /*
     * 注册stomp的端点
     */
    /*@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/endpointService").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/queue", "/topic");
        //客户端必须访问该域
        registry.setApplicationDestinationPrefixes("/app");

        //
        registry.setUserDestinationPrefix("/user/");
    }*/


}
