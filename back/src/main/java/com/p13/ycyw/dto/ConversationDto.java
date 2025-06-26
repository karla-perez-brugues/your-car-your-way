package com.p13.ycyw.dto;

import com.p13.ycyw.enums.ConversationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ConversationDto {
    private Long id;
    private String customerFullName;
    private ConversationStatus status;
    private List<MessageDto> messages;
    private String lastMessageContent;
}
