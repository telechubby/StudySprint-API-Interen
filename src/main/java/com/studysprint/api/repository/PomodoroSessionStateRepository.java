package com.studysprint.api.repository;

import com.studysprint.api.model.logic.PomodoroSessionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PomodoroSessionStateRepository extends JpaRepository<PomodoroSessionState, Long> {
}
