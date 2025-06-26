package com.p13.ycyw.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {

    @OneToOne(mappedBy = "customer")
    private Conversation conversation;

    public Customer(String email, String lastName, String firstName, String password, LocalDateTime createdAt) {
        this.setEmail(email);
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setPassword(password);
        this.setCreatedAt(createdAt);
        this.setAdmin(false);
    }
}
