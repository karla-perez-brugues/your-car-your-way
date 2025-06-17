package com.projet_13.your_car_your_way.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User {
    public Client() {
    }

    public Client(String email, String phoneNumber, String lastName, String firstName, String password, LocalDateTime createdAt) {
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setPassword(password);
        this.setCreatedAt(createdAt);
        this.setAdmin(false);
    }
}
