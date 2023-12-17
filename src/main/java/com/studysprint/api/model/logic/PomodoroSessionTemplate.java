package com.studysprint.api.model.logic;

import com.studysprint.api.model.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PomodoroSessionTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer workMinutes;
    private Integer breakMinutes;
    private Integer numberOfCycles;
    private Boolean isPublic;
    @ManyToOne
    private User createdBy;
}
