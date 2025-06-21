package com.projet_13.your_car_your_way.service;

import com.projet_13.your_car_your_way.dto.MessageDto;
import com.projet_13.your_car_your_way.enums.UserType;
import com.projet_13.your_car_your_way.model.Conversation;
import com.projet_13.your_car_your_way.model.Message;
import com.projet_13.your_car_your_way.model.User;
import com.projet_13.your_car_your_way.repository.ConversationRepository;
import com.projet_13.your_car_your_way.repository.MessageRepository;
import com.projet_13.your_car_your_way.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final ConversationService conversationService;
    private final ModelMapper modelMapper;

    public MessageService(
            MessageRepository messageRepository,
            UserRepository userRepository,
            ConversationRepository conversationRepository,
            ConversationService conversationService,
            ModelMapper modelMapper
    ) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.conversationService = conversationService;
        this.modelMapper = modelMapper;
    }

    public Message create(MessageDto messageDto, Conversation conversation, User user) {
        Message message = modelMapper.map(messageDto, Message.class);
        message.setConversation(conversation);
        message.setSender(user);
        updateConversation(user.getUserType(), conversation);

        return messageRepository.save(message);
    }

    public Message reply(MessageDto messageDto, String userEmail) throws NotFoundException {
        User user = userRepository.findByEmail(userEmail).orElseThrow(NotFoundException::new);
        Conversation conversation = conversationRepository.findById(messageDto.getConversationId()).orElseThrow(NotFoundException::new);

        return create(messageDto, conversation, user);
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

    private void updateConversation(UserType senderType, Conversation conversation) {
        switch (senderType) {
            case ADMIN:
                conversationService.markAsReplied(conversation);
                break;
            case CUSTOMER:
                conversationService.markAsPending(conversation);
                break;
        }
    }
}
