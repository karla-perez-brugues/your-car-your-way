package com.projet_13.your_car_your_way.service;

import com.projet_13.your_car_your_way.dto.ConversationDto;
import com.projet_13.your_car_your_way.dto.MessageDto;
import com.projet_13.your_car_your_way.enums.ConversationStatus;
import com.projet_13.your_car_your_way.enums.UserType;
import com.projet_13.your_car_your_way.model.Customer;
import com.projet_13.your_car_your_way.model.Conversation;
import com.projet_13.your_car_your_way.model.Message;
import com.projet_13.your_car_your_way.model.User;
import com.projet_13.your_car_your_way.repository.CustomerRepository;
import com.projet_13.your_car_your_way.repository.ConversationRepository;
import com.projet_13.your_car_your_way.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
        messageService.create(messageDto, conversation, customer);
        updateConversation(customer.getUserType(), conversation);

        return conversationRepository.save(conversation);
    }

    public Conversation reply(MessageDto messageDto, String userEmail) throws NotFoundException {
        User user = userRepository.findByEmail(userEmail).orElseThrow(NotFoundException::new);
        Conversation conversation = conversationRepository.findById(messageDto.getConversationId()).orElseThrow(NotFoundException::new);
        messageService.create(messageDto, conversation, user);
        updateConversation(user.getUserType(), conversation);

        return conversationRepository.save(conversation);
    }

    public Conversation findById(Long id) throws NotFoundException {
        return conversationRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Conversation> findAll() {
        return conversationRepository.findAll();
    }

    public Optional<Conversation> findByCustomer(String customerEmail) throws NotFoundException {
        Customer customer = customerRepository.findByEmail(customerEmail).orElseThrow(NotFoundException::new);

        return Optional.ofNullable(conversationRepository.findByCustomer(customer).orElseThrow(NotFoundException::new));
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

        for (Message message : conversation.getMessages()) {
            MessageDto messageDto = modelMapper.map(message, MessageDto.class);
            conversationDto.getMessages().add(messageDto);
        }

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
