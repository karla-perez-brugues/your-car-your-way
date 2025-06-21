package com.projet_13.your_car_your_way.dto;

import com.projet_13.your_car_your_way.enums.ConversationStatus;

import java.util.List;

public class ConversationDto {
    private Long id;
    private String customerFullName;
    private ConversationStatus status;
    private List<MessageDto> messages;

    public ConversationDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public ConversationStatus getStatus() {
        return status;
    }

    public void setStatus(ConversationStatus status) {
        this.status = status;
    }

    public List<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
    }
}
