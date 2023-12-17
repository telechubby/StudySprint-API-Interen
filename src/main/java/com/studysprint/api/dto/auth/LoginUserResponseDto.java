package com.studysprint.api.dto.auth;

import com.studysprint.api.model.auth.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserResponseDto {
    private User user;
    private String jwt;
}
