package com.studysprint.api.repository;

import com.studysprint.api.model.auth.User;
import com.studysprint.api.model.logic.PomodoroSession;
import com.studysprint.api.model.logic.SessionStateType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface PomodoroSessionRepository extends JpaRepository<PomodoroSession, Long> {
    Set<PomodoroSession> findByModerator(User moderator);
}
