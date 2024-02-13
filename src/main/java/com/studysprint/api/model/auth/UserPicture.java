package com.studysprint.api.model.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserPicture {
    @Id
    private Long id;
    @OneToOne
    private User user;
    private String imageBase64;
    public UserPicture(User user, String imageBase64) {
        this.user = user;
        this.imageBase64 = imageBase64;
    }
}
