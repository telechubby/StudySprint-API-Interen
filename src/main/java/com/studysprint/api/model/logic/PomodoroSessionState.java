package com.studysprint.api.model.logic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PomodoroSessionState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime sessionStarted;
    private LocalDateTime lastSessionUpdate;
    @ManyToOne
    private SessionStateType sessionState;
    private Integer cycleNumber;
}
