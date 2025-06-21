package com.projet_13.your_car_your_way.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {

    @OneToOne(mappedBy = "customer")
    private Conversation conversation;

    public Customer() {
    }

    public Customer(String email, String phoneNumber, String lastName, String firstName, String password, LocalDateTime createdAt) {
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setPassword(password);
        this.setCreatedAt(createdAt);
        this.setAdmin(false);
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
