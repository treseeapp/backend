package com.tresee.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private Environment environment;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*
         * Habilitamos las opciones alternativas al WebSocket por si
         * la conexión WebSocket no estuviera disponible
         * en este endpoint, así como el mismo WebSocket gracias al STOMP
         */
        registry.addEndpoint("/socket")
                .setAllowedOrigins(environment.getProperty("cors.allowed"))
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /*
         * Aquí configuramos el intermediario del mensaje
         * habilitamos un agente de mensajes simple basado en memoria.
         */
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/chat");
    }
}
