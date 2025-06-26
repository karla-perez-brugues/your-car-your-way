package com.p13.ycyw.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.p13.ycyw.enums.UserType;
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
    private boolean admin;
    private UserType userType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

