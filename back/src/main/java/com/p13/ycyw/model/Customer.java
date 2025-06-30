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
}
