package com.projet_13.your_car_your_way.controller;

import com.projet_13.your_car_your_way.dto.ConversationDto;
import com.projet_13.your_car_your_way.model.Conversation;
import com.projet_13.your_car_your_way.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/front-office-home")
@CrossOrigin(origins = "*", maxAge = 3600)
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
