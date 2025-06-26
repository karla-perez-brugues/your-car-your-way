package com.p13.ycyw.repository;

import com.p13.ycyw.model.Conversation;
import com.p13.ycyw.model.Customer;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByCustomer(@NotNull Customer customer);
}
