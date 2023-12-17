package com.studysprint.api.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserDto {
    private String name;
    private String username;
    private String password;
    @JsonIgnore
    public boolean isComplete(){
        return name != null && username != null && password != null;
    }
}
