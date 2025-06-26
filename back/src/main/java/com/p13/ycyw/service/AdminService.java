package com.p13.ycyw.service;

import com.p13.ycyw.controller.payload.request.SignupRequest;
import com.p13.ycyw.model.Admin;
import com.p13.ycyw.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void create(SignupRequest signupRequest) {
        Admin admin = new Admin();
        admin.setEmail(signupRequest.getEmail());
        admin.setLastName(signupRequest.getLastName());
        admin.setFirstName(signupRequest.getFirstName());
        admin.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        admin.setAdmin(true);

        adminRepository.save(admin);
    }
}
