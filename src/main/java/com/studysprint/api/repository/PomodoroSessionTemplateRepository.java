package com.studysprint.api.repository;

import com.studysprint.api.model.auth.User;
import com.studysprint.api.model.logic.PomodoroSessionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PomodoroSessionTemplateRepository extends JpaRepository<PomodoroSessionTemplate, Long> {
    @Query("SELECT pst FROM PomodoroSessionTemplate pst WHERE pst.id = ?1 AND (pst.isPublic = true OR pst.createdBy = ?2)")
    Optional<PomodoroSessionTemplate> findByIdAndUser(Long sessionId, User user);
    @Query("SELECT pst FROM PomodoroSessionTemplate pst WHERE (pst.isPublic = true OR pst.createdBy = ?1)")
    List<PomodoroSessionTemplate> findByUser(User user);
}
