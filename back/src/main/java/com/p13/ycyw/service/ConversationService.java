package com.p13.ycyw.service;

import com.p13.ycyw.dto.ConversationDto;
import com.p13.ycyw.dto.MessageDto;
import com.p13.ycyw.enums.ConversationStatus;
import com.p13.ycyw.enums.UserType;
import com.p13.ycyw.exception.NotFoundException;
import com.p13.ycyw.model.Conversation;
import com.p13.ycyw.model.Customer;
import com.p13.ycyw.model.Message;
import com.p13.ycyw.model.User;
import com.p13.ycyw.repository.ConversationRepository;
import com.p13.ycyw.repository.CustomerRepository;
import com.p13.ycyw.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    public Conversation create(MessageDto messageDto,String customerEmail) throws NotFoundException {
        Customer customer = customerRepository.findByEmail(customerEmail).orElseThrow(NotFoundException::new);
        Conversation conversation = new Conversation(customer);
        conversation = conversationRepository.save(conversation);
        messageService.create(messageDto, conversation, customer);
        updateConversation(customer.getUserType(), conversation);

        return conversation;
    }

    public void reply(MessageDto messageDto, String userEmail) throws NotFoundException {
        User user = userRepository.findByEmail(userEmail).orElseThrow(NotFoundException::new);
        Conversation conversation = conversationRepository.findById(messageDto.getConversationId()).orElseThrow(NotFoundException::new);
        messageService.create(messageDto, conversation, user);
        updateConversation(user.getUserType(), conversation);

        conversationRepository.save(conversation);
    }

    public Conversation findById(Long id) throws NotFoundException {
        return conversationRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Conversation> findAll() {
        return conversationRepository.findAll();
    }

    public Optional<Conversation> findByCustomer(String customerEmail) throws NotFoundException {
        Optional<Customer> customer = customerRepository.findByEmail(customerEmail);

        if (customer.isPresent()) {
            return conversationRepository.findByCustomer(customer.get());
        }

        return Optional.empty();
    }

    // todo: maybe this needs to move
    public void markAsReplied(Conversation conversation) {
        conversation.setStatus(ConversationStatus.REPLIED);

        conversationRepository.save(conversation);
    }

    // todo: maybe this needs to move
    public void markAsPending(Conversation conversation) {
        conversation.setStatus(ConversationStatus.PENDING);

        conversationRepository.save(conversation);
    }

    public ConversationDto entityToDto(Conversation conversation) {
        ConversationDto conversationDto = modelMapper.map(conversation, ConversationDto.class);
        conversationDto.setCustomerFullName(conversation.getCustomer().getFirstName() + " " + conversation.getCustomer().getLastName());

        List<Message> messages = conversation.getMessages();
        Message lastMessage = messages.get(messages.size() - 1);
        conversationDto.setLastMessageContent(lastMessage.getContent());

        return conversationDto;
    }

    private void updateConversation(UserType senderType, Conversation conversation) {
        switch (senderType) {
            case ADMIN:
                markAsReplied(conversation);
                break;
            case CUSTOMER:
                markAsPending(conversation);
                break;
        }
    }
}
