package com.studysprint.api.repository;

import com.studysprint.api.model.logic.SessionStateType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionStateTypeRepository extends JpaRepository<SessionStateType, Long> {
    Optional<SessionStateType> findByName(String name);
}
