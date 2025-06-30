package com.p13.ycyw.service;

import com.p13.ycyw.controller.payload.request.SignupRequest;
import com.p13.ycyw.exception.NotFoundException;
import com.p13.ycyw.model.Customer;
import com.p13.ycyw.model.Role;
import com.p13.ycyw.repository.CustomerRepository;
import com.p13.ycyw.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public void create(SignupRequest signupRequest) {
        Customer customer = new Customer();
        customer.setEmail(signupRequest.getEmail());
        customer.setLastName(signupRequest.getLastName());
        customer.setFirstName(signupRequest.getFirstName());
        customer.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        Role role = roleRepository.findByName("ROLE_CUSTOMER").orElseThrow(NotFoundException::new);
        customer.setRole(role);

        customerRepository.save(customer);
    }
}
