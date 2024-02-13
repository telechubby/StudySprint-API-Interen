package com.studysprint.api.web;

import com.studysprint.api.dto.auth.TokenUserDto;
import com.studysprint.api.model.auth.User;
import com.studysprint.api.model.auth.UserPicture;
import com.studysprint.api.service.auth.AuthenticationService;
import com.studysprint.api.service.auth.UserPictureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/picture")
public class UserPictureController {
    private final UserPictureService userPictureService;
    private final AuthenticationService authenticationService;

    public UserPictureController(UserPictureService userPictureService, AuthenticationService authenticationService) {
        this.userPictureService = userPictureService;
        this.authenticationService = authenticationService;
    }

    @GetMapping()
    public ResponseEntity<Object> getUserImage(@RequestHeader("Authorization") String authorizationHeader)
    {
        String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
        TokenUserDto userDto = authenticationService.decodeJwtToken(token);
        User user = userDto.getUser();
        UserPicture picture = userPictureService.findByUser(user);
        return new ResponseEntity<>(picture.getImageBase64(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> setUserImage(@RequestHeader("Authorization") String authorizationHeader, @RequestBody String imageBase64)
    {
        String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
        TokenUserDto userDto = authenticationService.decodeJwtToken(token);
        User user = userDto.getUser();
        UserPicture picture = userPictureService.setUserPicture(user, imageBase64);
        return new ResponseEntity<>("Picture set to user "+user.getName(), HttpStatus.OK);
    }
}
