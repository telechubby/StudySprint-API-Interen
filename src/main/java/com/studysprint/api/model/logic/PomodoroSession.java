package com.studysprint.api.model.logic;

import com.studysprint.api.model.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PomodoroSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private PomodoroSessionState sessionState;
    @ManyToOne
    private PomodoroSessionTemplate sessionTemplate;
    @ManyToOne
    private User moderator;
    @ManyToMany
    @JoinTable(
            name = "session_members",
            joinColumns = {@JoinColumn(name = "session_id")},
            inverseJoinColumns = {@JoinColumn(name = "member_id")}
    )
    private Set<User> members;
}
