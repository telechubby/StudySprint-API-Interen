package com.studysprint.api.repository;

import com.studysprint.api.model.logic.PomodoroSessionState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PomodoroSessionStateRepository extends JpaRepository<PomodoroSessionState, Long> {
}
