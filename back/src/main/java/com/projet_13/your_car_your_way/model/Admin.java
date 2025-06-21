package com.projet_13.your_car_your_way.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    public Admin() {
    }

    public Admin(String email, String phoneNumber, String lastName, String firstName, String password, LocalDateTime createdAt) {
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setPassword(password);
        this.setCreatedAt(createdAt);
        this.setAdmin(true);
    }
}
