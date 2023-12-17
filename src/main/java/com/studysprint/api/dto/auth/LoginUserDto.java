package com.studysprint.api.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserDto {
    private String username;
    private String password;

    public LoginUserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
    @JsonIgnore
    public boolean isComplete(){
        return username != null && password != null;
    }
}
