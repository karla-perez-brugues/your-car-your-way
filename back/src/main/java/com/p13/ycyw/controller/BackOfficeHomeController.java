package com.p13.ycyw.controller;

import com.p13.ycyw.dto.ConversationDto;
import com.p13.ycyw.exception.BadRequestException;
import com.p13.ycyw.model.Conversation;
import com.p13.ycyw.model.User;
import com.p13.ycyw.service.ConversationService;
import com.p13.ycyw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/back-office")
public class BackOfficeHomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConversationService conversationService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<ConversationDto>> home(Principal principal) throws BadRequestException {
        User user = userService.findByEmail(principal.getName());

        List<Conversation> conversations = conversationService.findAll();
        List<ConversationDto> conversationDtoList = conversations.stream().map(conversationService::entityToDto).toList();

        return ResponseEntity.ok(conversationDtoList);
    }

}
