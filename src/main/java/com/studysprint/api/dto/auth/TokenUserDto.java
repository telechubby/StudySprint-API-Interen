package com.studysprint.api.dto.auth;

import com.studysprint.api.model.auth.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenUserDto {
    private User user;
    private Instant iat;
    private Instant exp;
}
