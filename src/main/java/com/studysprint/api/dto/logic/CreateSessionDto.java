package com.studysprint.api.dto.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSessionDto {
    private Long templateId;
    @JsonIgnore
    public boolean isComplete(){
        return templateId != null;
    }
}
