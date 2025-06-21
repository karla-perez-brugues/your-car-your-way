package com.projet_13.your_car_your_way.service;

import com.projet_13.your_car_your_way.dto.ConversationDto;
import com.projet_13.your_car_your_way.dto.MessageDto;
import com.projet_13.your_car_your_way.enums.ConversationStatus;
import com.projet_13.your_car_your_way.model.Customer;
import com.projet_13.your_car_your_way.model.Conversation;
import com.projet_13.your_car_your_way.model.Message;
import com.projet_13.your_car_your_way.repository.CustomerRepository;
import com.projet_13.your_car_your_way.repository.ConversationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final MessageService messageService;

    public ConversationService(
            ConversationRepository conversationRepository,
            CustomerRepository customerRepository,
            ModelMapper modelMapper,
            MessageService messageService
    ) {
        this.conversationRepository = conversationRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.messageService = messageService;
    }

    public Conversation create(MessageDto messageDto,String customerEmail) throws NotFoundException {
        Customer customer = customerRepository.findByEmail(customerEmail).orElseThrow(NotFoundException::new);
        Conversation conversation = new Conversation(customer);
        messageService.create(messageDto, conversation, customer);

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
//        Conversation conversation = conversationRepository.findById(id).orElseThrow(NotFoundException::new);
        conversation.setStatus(ConversationStatus.REPLIED);

        conversationRepository.save(conversation);
    }

    // todo: maybe this needs to move
    public void markAsPending(Conversation conversation) {
//        Conversation conversation = conversationRepository.findById(id).orElseThrow(NotFoundException::new);
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
}
