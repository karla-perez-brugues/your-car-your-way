package com.p13.ycyw.service;


import com.p13.ycyw.controller.payload.request.SignupRequest;
import com.p13.ycyw.enums.UserRole;
import com.p13.ycyw.exception.NotFoundException;
import com.p13.ycyw.model.Conversation;
import com.p13.ycyw.model.User;
import com.p13.ycyw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ConversationService conversationService;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(NotFoundException::new);
    }

    public void create(SignupRequest signUpRequest) {
        if (signUpRequest.getUserType() == UserRole.ADMIN) {
            this.adminService.create(signUpRequest);
        } else {
            this.customerService.create(signUpRequest);
        }
    }

    public boolean hasConversation(String email) {
        Optional<Conversation> conversation = this.conversationService.findByCustomer(email);

        return conversation.isPresent();
    }

}
