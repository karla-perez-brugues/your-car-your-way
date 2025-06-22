package com.projet_13.your_car_your_way.service;

import com.projet_13.your_car_your_way.dto.MessageDto;
import com.projet_13.your_car_your_way.model.Conversation;
import com.projet_13.your_car_your_way.model.Message;
import com.projet_13.your_car_your_way.model.User;
import com.projet_13.your_car_your_way.repository.ConversationRepository;
import com.projet_13.your_car_your_way.repository.MessageRepository;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

    public Message create(MessageDto messageDto, Conversation conversation, User user) {
        Message message = modelMapper.map(messageDto, Message.class);
        message.setConversation(conversation);
        message.setSender(user);

        return messageRepository.save(message);
    }

    public List<Message> findAllByConversation(Long conversationId, User user) throws NotFoundException, BadRequestException {
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(NotFoundException::new);
        if (!conversation.getCustomer().equals(user) && user.isAdmin()) {
            throw new BadRequestException();
        }

        return conversation.getMessages();
    }

    public MessageDto entityToDto(Message message) {
        MessageDto messageDto = modelMapper.map(message, MessageDto.class);
        messageDto.setConversationId(message.getConversation().getId());
        messageDto.setSenderType(message.getSender().getUserType());

        return messageDto;
    }
}
