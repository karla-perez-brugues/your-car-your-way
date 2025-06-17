package com.projet_13.your_car_your_way.model;

import com.projet_13.your_car_your_way.enums.ConversationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "CONVERSATIONS")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ConversationStatus status;

    public Conversation() {
    }

    public Conversation(Client client, ConversationStatus status) {
        this.client = client;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ConversationStatus getStatus() {
        return status;
    }

    public void setStatus(ConversationStatus status) {
        this.status = status;
    }
}
