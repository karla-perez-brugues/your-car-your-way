package com.p13.ycyw.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    public Admin(String email, String phoneNumber, String lastName, String firstName, String password, LocalDateTime createdAt) {
        this.setEmail(email);
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setPassword(password);
        this.setCreatedAt(createdAt);
        this.setAdmin(true);
    }
}
