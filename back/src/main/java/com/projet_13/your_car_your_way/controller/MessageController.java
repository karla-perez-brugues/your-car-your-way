package com.projet_13.your_car_your_way.controller;

import com.projet_13.your_car_your_way.dto.MessageDto;
import com.projet_13.your_car_your_way.model.Message;
import com.projet_13.your_car_your_way.model.User;
import com.projet_13.your_car_your_way.service.MessageService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/message")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("")
    public ResponseEntity<MessageDto> reply(@Valid @RequestBody MessageDto messageDto, Principal principal) throws NotFoundException {
        Message message = messageService.reply(messageDto, principal.getName());
        MessageDto flushedMessageDto = messageService.entityToDto(message);

        return ResponseEntity.ok(flushedMessageDto);
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<List<MessageDto>> listByConversation(Principal principal, @PathVariable("conversationId") String conversationId) throws NotFoundException, BadRequestException {
        User user = (User) principal; // fixme: maybe this doesn't work
        List<Message> messageList = messageService.findAllByConversation(Long.valueOf(conversationId), user);
        List<MessageDto> messageDtoList = messageList.stream().map(messageService::entityToDto).toList();

        return ResponseEntity.ok(messageDtoList);
    }
}
