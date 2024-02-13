package com.studysprint.api.web;

import com.studysprint.api.dto.auth.TokenUserDto;
import com.studysprint.api.model.auth.User;
import com.studysprint.api.model.auth.UserPicture;
import com.studysprint.api.service.auth.AuthenticationService;
import com.studysprint.api.service.auth.UserPictureService;
import com.studysprint.api.service.auth.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/friends")
public class UserFriendsController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserFriendsController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/addById")
    public ResponseEntity<Object> addFriend(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Long friendId)
    {
        String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
        TokenUserDto userDto = authenticationService.decodeJwtToken(token);
        User user = userDto.getUser();
        User friend = userService.findById(friendId);
        if(friend == null)
            return new ResponseEntity<>("User with id "+friendId+" doesn't exist", HttpStatus.BAD_REQUEST);
        else{
            user = userService.addFriend(user, friend);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @DeleteMapping("/removeById")
    public ResponseEntity<Object> removeFriend(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Long friendId)
    {
        String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
        TokenUserDto userDto = authenticationService.decodeJwtToken(token);
        User user = userDto.getUser();
        User friend = userService.findById(friendId);
        if(friend == null)
            return new ResponseEntity<>("User with id "+friendId+" doesn't exist", HttpStatus.BAD_REQUEST);
        else{
            user = userService.removeFriend(user, friend);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addFriendByCode(@RequestHeader("Authorization") String authorizationHeader, @RequestBody String friendCode)
    {
        String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
        TokenUserDto userDto = authenticationService.decodeJwtToken(token);
        User user = userDto.getUser();
        User friend = userService.findByCode(friendCode);
        if(friend == null)
            return new ResponseEntity<>("User with code "+friendCode+" doesn't exist", HttpStatus.BAD_REQUEST);
        else{
            user = userService.addFriend(user, friend);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Object> removeFriendByCode(@RequestHeader("Authorization") String authorizationHeader, @RequestBody String friendCode)
    {
        String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
        TokenUserDto userDto = authenticationService.decodeJwtToken(token);
        User user = userDto.getUser();
        User friend = userService.findByCode(friendCode);
        if(friend == null)
            return new ResponseEntity<>("User with code "+friendCode+" doesn't exist", HttpStatus.BAD_REQUEST);
        else{
            user = userService.removeFriend(user, friend);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getFriends(@RequestHeader("Authorization") String authorizationHeader){
        String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
        TokenUserDto userDto = authenticationService.decodeJwtToken(token);
        User user = userDto.getUser();
        return new ResponseEntity<>(user.getFriends(), HttpStatus.OK);
    }
}
