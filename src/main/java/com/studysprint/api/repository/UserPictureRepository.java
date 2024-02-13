package com.studysprint.api.repository;

import com.studysprint.api.model.auth.UserPicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPictureRepository extends JpaRepository<UserPicture, Long> {
}
