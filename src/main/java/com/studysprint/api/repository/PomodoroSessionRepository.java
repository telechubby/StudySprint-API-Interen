package com.studysprint.api.repository;

import com.studysprint.api.model.auth.User;
import com.studysprint.api.model.logic.PomodoroSession;
import com.studysprint.api.model.logic.SessionStateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PomodoroSessionRepository extends JpaRepository<PomodoroSession, Long>{
    List<PomodoroSession> findByModeratorAndSessionState_Type(User moderator, SessionStateType sessionStateType);

    Optional<PomodoroSession> findByModeratorAndId(User user, Long sessionId);

    Optional<PomodoroSession> findByModeratorAndIdAndSessionState_Type(User moderator, Long id, SessionStateType sessionStateType);
    @Query("SELECT COUNT(pss) FROM PomodoroSession pss " +
            "JOIN pss.sessionState session " +
            "JOIN pss.sessionTemplate template " +
            "WHERE session.cycleNumber = template.numberOfCycles " +
            "AND FUNCTION('DATE', session.lastSessionUpdate) = FUNCTION('DATE', :givenDate)" +
            "AND pss.moderator = :user")
    long countByCycleNumberAndSessionStarted(@Param("givenDate") LocalDate givenDate, @Param("user") User user);
}
