package com.projet_13.your_car_your_way.repository;

import com.projet_13.your_car_your_way.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
