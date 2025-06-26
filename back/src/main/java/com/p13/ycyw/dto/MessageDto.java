package com.p13.ycyw.dto;

import com.p13.ycyw.enums.UserType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {
    private Long id;
    private Long conversationId;
    @NotNull
    @Size(max = 2500)
    private String content;
    private UserType senderType;
    private LocalDateTime createdAt;
}
