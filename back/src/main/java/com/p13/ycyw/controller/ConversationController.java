package com.p13.ycyw.controller;

import com.p13.ycyw.dto.ConversationDto;
import com.p13.ycyw.dto.MessageDto;
import com.p13.ycyw.enums.UserType;
import com.p13.ycyw.exception.BadRequestException;
import com.p13.ycyw.exception.NotFoundException;
import com.p13.ycyw.model.Conversation;
import com.p13.ycyw.model.User;
import com.p13.ycyw.service.ConversationService;
import com.p13.ycyw.service.UserService;
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

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ConversationDto> show(@PathVariable("id") String id) throws NotFoundException {
        Conversation conversation = conversationService.findById(Long.valueOf(id));
        ConversationDto conversationDto = conversationService.entityToDto(conversation);

        return ResponseEntity.ok(conversationDto);
    }

    @PostMapping("")
    public ResponseEntity<ConversationDto> create(@Valid @RequestBody MessageDto messageDto, Principal principal) throws NotFoundException {
        User user = userService.findByEmail(principal.getName());

        if (user.getUserType() != UserType.CUSTOMER) {
            throw new BadRequestException();
        }

        Conversation conversation = conversationService.create(messageDto, principal.getName());
        ConversationDto conversationDto = conversationService.entityToDto(conversation);

        return ResponseEntity.ok(conversationDto);
    }

}
