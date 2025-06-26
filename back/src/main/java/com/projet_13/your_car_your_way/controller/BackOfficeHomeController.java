package com.projet_13.your_car_your_way.controller;

import com.projet_13.your_car_your_way.dto.ConversationDto;
import com.projet_13.your_car_your_way.model.Conversation;
import com.projet_13.your_car_your_way.model.User;
import com.projet_13.your_car_your_way.service.ConversationService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/back-office-home")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BackOfficeHomeController {

    @Autowired
    private ConversationService conversationService;

    @GetMapping("")
    public ResponseEntity<List<ConversationDto>> home(Principal principal) throws BadRequestException {
        User user = (User) principal; // fixme: not sure this works

        if (!user.isAdmin()) {
            throw new BadRequestException();
        }

        List<Conversation> conversations = conversationService.findAll();
        List<ConversationDto> conversationDtoList = conversations.stream().map(conversationService::entityToDto).toList();

        return ResponseEntity.ok(conversationDtoList);
    }

}
