package com.p13.ycyw.service;

import com.p13.ycyw.controller.payload.request.SignupRequest;
import com.p13.ycyw.model.Customer;
import com.p13.ycyw.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void create(SignupRequest signupRequest) {
        Customer customer = new Customer();
        customer.setEmail(signupRequest.getEmail());
        customer.setLastName(signupRequest.getLastName());
        customer.setFirstName(signupRequest.getFirstName());
        customer.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        customer.setAdmin(false);

        customerRepository.save(customer);
    }
}
