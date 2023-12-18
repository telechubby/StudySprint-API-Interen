package com.studysprint.api.repository;

import com.studysprint.api.model.auth.User;
import com.studysprint.api.model.logic.PomodoroSession;
import com.studysprint.api.model.logic.SessionStateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PomodoroSessionRepository extends JpaRepository<PomodoroSession, Long> {
    List<PomodoroSession> findByModeratorAndSessionState_SessionState(User moderator, SessionStateType sessionStateType);

    Optional<PomodoroSession> findByModeratorAndId(User user, Long sessionId);

    Optional<PomodoroSession> findByModeratorAndIdAndSessionState_SessionState(User moderator, Long id, SessionStateType sessionStateType);
}
