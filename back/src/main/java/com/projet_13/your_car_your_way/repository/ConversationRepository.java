package com.projet_13.your_car_your_way.repository;

import com.projet_13.your_car_your_way.model.Customer;
import com.projet_13.your_car_your_way.model.Conversation;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByCustomer(@NotNull Customer customer);
}
