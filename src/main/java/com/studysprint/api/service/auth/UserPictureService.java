package com.studysprint.api.service.auth;

import com.studysprint.api.model.auth.User;
import com.studysprint.api.model.auth.UserPicture;
import com.studysprint.api.repository.UserPictureRepository;
import com.studysprint.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserPictureService{
    private final UserRepository userRepository;
    private final UserPictureRepository pictureRepository;

    public UserPictureService(UserRepository userRepository, UserPictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
    }

    public UserPicture findById(Long id){
        return pictureRepository.findById(id).orElse(null);
    }

    public UserPicture findByUser(User user){
        return pictureRepository.findByUser(user).orElse(null);
    }

    public UserPicture setUserPicture(User user, String imageBase64){
        UserPicture up = pictureRepository.findByUser(user).orElse(null);
        if(up != null)
            up.setImageBase64(imageBase64);
        up = new UserPicture(user, imageBase64);
        return pictureRepository.save(up);
    }
}
