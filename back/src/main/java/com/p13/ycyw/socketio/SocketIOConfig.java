package com.p13.ycyw.socketio;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {
    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(9093);
        config.setOrigin("http://localhost:4200");
        config.setPingInterval(25000);
        config.setPingTimeout(60000);
        return new SocketIOServer(config);
    }
}
