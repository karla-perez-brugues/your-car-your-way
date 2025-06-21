package com.projet_13.your_car_your_way.dto;

import com.projet_13.your_car_your_way.enums.UserType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class MessageDto {
    private Long id;

    private Long conversationId;

    @NotNull
    @Size(max = 2500)
    private String content;

    private UserType senderType;

    private LocalDateTime createdAt;

    public MessageDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserType getSenderType() {
        return senderType;
    }

    public void setSenderType(UserType senderType) {
        this.senderType = senderType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
