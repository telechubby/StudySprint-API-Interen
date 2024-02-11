package com.studysprint.api.web;

import com.studysprint.api.dto.auth.LoginUserDto;
import com.studysprint.api.dto.auth.RegisterUserDto;
import com.studysprint.api.model.auth.Role;
import com.studysprint.api.model.auth.User;
import com.studysprint.api.service.auth.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
    private final AuthenticationService authService;
    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginUserDto userForm){
        try{
            if(!userForm.isComplete())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            String jwt = authService.login(userForm).getJwt();
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserDto userForm)
    {
        try{
            if(!userForm.isComplete())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            authService.register(userForm);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/isGranted")
    public ResponseEntity<Boolean> isGranted(@RequestHeader("Authorization") String authorizationHeader, @RequestBody String roleName)
    {
        String token = authService.extractTokenFromAuthorizationHeader(authorizationHeader);
        User user = authService.decodeJwtToken(token).getUser();
        Role role = authService.getRoleByName(roleName);
        return new ResponseEntity<>(user.getAuthorities().contains(role), HttpStatus.OK);
    }

    @GetMapping("/isHimself")
    public ResponseEntity<Boolean> isHimself(@RequestHeader("Authorization") String authorizationHeader, @RequestBody String username)
    {
        String token = authService.extractTokenFromAuthorizationHeader(authorizationHeader);
        User tokenUser = authService.decodeJwtToken(token).getUser();
        User usernameUser = authService.getUserByUsername(username);
        if(tokenUser == null || usernameUser == null)
            return new ResponseEntity<>(false, HttpStatus.OK);
        return new ResponseEntity<>(tokenUser.getId().equals(usernameUser.getId()), HttpStatus.OK);
    }

}
