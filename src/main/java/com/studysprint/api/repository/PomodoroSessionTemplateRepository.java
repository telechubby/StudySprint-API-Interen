package com.studysprint.api.repository;

import com.studysprint.api.model.auth.User;
import com.studysprint.api.model.logic.PomodoroSessionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PomodoroSessionTemplateRepository extends JpaRepository<PomodoroSessionTemplate, Long> {
    Optional<PomodoroSessionTemplate> findByIdAndCreatedByOrIsPublicTrue(Long sessionId, User user);
    List<PomodoroSessionTemplate> findByCreatedByOrIsPublicTrue(User user);
}
