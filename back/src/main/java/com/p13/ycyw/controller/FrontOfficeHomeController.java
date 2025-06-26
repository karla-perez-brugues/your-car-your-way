package com.p13.ycyw.controller;

import com.p13.ycyw.dto.ConversationDto;
import com.p13.ycyw.exception.NotFoundException;
import com.p13.ycyw.model.Conversation;
import com.p13.ycyw.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/front-office")
public class FrontOfficeHomeController {

    @Autowired
    private ConversationService conversationService;

    @GetMapping("")
    public ResponseEntity<?> home(Principal principal) throws NotFoundException {
        String currentEmail = principal.getName();
        Optional<Conversation> conversation = conversationService.findByCustomer(currentEmail);
        if (conversation.isPresent()) {
            ConversationDto conversationDto = conversationService.entityToDto(conversation.get());
            return new ResponseEntity<>(conversationDto, HttpStatus.OK);
        }

        return ResponseEntity.ok().build();
    }

}
