package com.studysprint.api.repository;

import com.studysprint.api.model.auth.User;
import com.studysprint.api.model.auth.UserPicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPictureRepository extends JpaRepository<UserPicture, Long> {
    Optional<UserPicture> findByUser(User user);
}
