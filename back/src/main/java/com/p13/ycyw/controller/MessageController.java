package com.p13.ycyw.controller;

import com.p13.ycyw.dto.MessageDto;
import com.p13.ycyw.exception.BadRequestException;
import com.p13.ycyw.exception.NotFoundException;
import com.p13.ycyw.model.Message;
import com.p13.ycyw.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/{conversationId}")
    public ResponseEntity<List<MessageDto>> listByConversation(Principal principal, @PathVariable("conversationId") String conversationId) throws NotFoundException, BadRequestException {
        List<Message> messageList = messageService.findAllByConversation(Long.valueOf(conversationId), principal.getName());
        List<MessageDto> messageDtoList = messageList.stream().map(messageService::entityToDto).toList();

        return ResponseEntity.ok(messageDtoList);
    }
}
