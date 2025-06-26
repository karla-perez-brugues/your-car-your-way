package com.p13.ycyw.socketio;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.p13.ycyw.dto.MessageDto;
import com.p13.ycyw.security.JwtUtils;
import com.p13.ycyw.service.ConversationService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MessagingHandler {

    private static final Logger logger = LoggerFactory.getLogger(MessagingHandler.class);

    private final SocketIOServerRunner server;
    private final ConversationService conversationService;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public MessagingHandler(
            SocketIOServerRunner server,
            ConversationService conversationService,
            JwtUtils jwtUtils,
            UserDetailsService userDetailsService
    ) {
        this.server = server;
        this.conversationService = conversationService;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    public void setupListener() {
        SocketIOServer socket = this.server.getSocket();

        // token management
        socket.addConnectListener(client -> {
            String jwt = client.getHandshakeData().getSingleUrlParam("token");

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                try {
                    String username = jwtUtils.getUserNameFromJwtToken(jwt);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    client.set("SPRING_SECURITY_CONTEXT", authentication);
                    logger.info("Successfully authenticated user");
                } catch (Exception e) {
                    logger.warn("Failed to authenticate user", e);
                    client.disconnect();
                }
            } else {
                logger.warn("Invalid or missing token.", client.getSessionId());
                client.disconnect();
            }
        });

        // message creation
        socket.addEventListener("sendMessage", MessageDto.class, (client, message, ackSender) -> {
            onChatMessage(client, message);
        });
    }

    private void onChatMessage(SocketIOClient client, MessageDto message) {
        logger.info("Received message from client {}: content={}",
                client.getSessionId(), message.getContent());
        try {
            // authenticate message sender
            Authentication authentication = client.get("SPRING_SECURITY_CONTEXT");
            this.conversationService.reply(message, authentication.getName());
            server.getSocket().getBroadcastOperations().sendEvent("chatMessage", message);

        } catch (Exception e) {
            logger.error("Error handling chat message: {}", e.getMessage(), e);
        }
    }
}
