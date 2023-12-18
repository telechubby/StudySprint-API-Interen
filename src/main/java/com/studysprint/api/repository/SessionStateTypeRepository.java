package com.studysprint.api.repository;

import com.studysprint.api.model.logic.SessionStateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionStateTypeRepository extends JpaRepository<SessionStateType, Long> {
    Optional<SessionStateType> findByName(String name);
}
