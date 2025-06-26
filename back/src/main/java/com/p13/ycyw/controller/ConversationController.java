package com.p13.ycyw.controller;

import com.p13.ycyw.dto.ConversationDto;
import com.p13.ycyw.dto.MessageDto;
import com.p13.ycyw.exception.NotFoundException;
import com.p13.ycyw.model.Conversation;
import com.p13.ycyw.service.ConversationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/conversation")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

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
