package com.projet_13.your_car_your_way.controller;

import com.projet_13.your_car_your_way.dto.ConversationDto;
import com.projet_13.your_car_your_way.dto.MessageDto;
import com.projet_13.your_car_your_way.model.Conversation;
import com.projet_13.your_car_your_way.service.ConversationService;
import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/conversation")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversationDto> show(@PathVariable("id") String id) throws NotFoundException {
        Conversation conversation = conversationService.findById(Long.valueOf(id));
        ConversationDto conversationDto = conversationService.entityToDto(conversation);

        return ResponseEntity.ok(conversationDto);
    }

    @PostMapping("")
    public ResponseEntity<ConversationDto> create(@Valid @RequestBody MessageDto messageDto, Principal principal) throws NotFoundException {
        Conversation conversation = conversationService.create(messageDto, principal.getName());
        ConversationDto conversationDto = conversationService.entityToDto(conversation);

        return ResponseEntity.ok(conversationDto);
    }

}
