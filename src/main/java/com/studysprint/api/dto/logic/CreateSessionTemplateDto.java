package com.studysprint.api.dto.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.studysprint.api.model.auth.User;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSessionTemplateDto {
    private Long id;
    private String name;
    private Integer workMinutes;
    private Integer breakMinutes;
    private Integer numberOfCycles;
    private Boolean isPublic;
    @JsonIgnore
    public boolean isComplete(){
        return name != null && workMinutes != null && breakMinutes != null && numberOfCycles != null && isPublic != null;
    }
}
