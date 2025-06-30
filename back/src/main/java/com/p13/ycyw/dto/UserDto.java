package com.p13.ycyw.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String phoneNumber;
    private String lastName;
    private String firstName;
    @JsonIgnore
    private String password;
    private String role;
    private boolean hasConversation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

