package com.p13.ycyw.service;

import com.p13.ycyw.dto.MessageDto;
import com.p13.ycyw.exception.BadRequestException;
import com.p13.ycyw.exception.NotFoundException;
import com.p13.ycyw.model.Conversation;
import com.p13.ycyw.model.Message;
import com.p13.ycyw.model.User;
import com.p13.ycyw.repository.ConversationRepository;
import com.p13.ycyw.repository.MessageRepository;
import com.p13.ycyw.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    public Message create(MessageDto messageDto, Conversation conversation, User user) {
        Message message = modelMapper.map(messageDto, Message.class);
        message.setConversation(conversation);
        message.setSender(user);

        return messageRepository.save(message);
    }

    public List<Message> findAllByConversation(Long conversationId, String email) throws NotFoundException, BadRequestException {
        User user = userRepository.findByEmail(email).orElseThrow(NotFoundException::new);
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(NotFoundException::new);
        boolean userIsAdmin = user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        if (!conversation.getCustomer().equals(user) && !userIsAdmin) {
            throw new BadRequestException();
        }

        return conversation.getMessages();
    }

    public MessageDto entityToDto(Message message) {
        MessageDto messageDto = modelMapper.map(message, MessageDto.class);
        messageDto.setConversationId(message.getConversation().getId());
        messageDto.setSenderType(message.getSender().getRole().getName());

        return messageDto;
    }
}
