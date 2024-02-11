package com.studysprint.api.repository;

import com.studysprint.api.model.logic.PomodoroSessionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PomodoroSessionStateRepository extends JpaRepository<PomodoroSessionState, Long> {
}
