package com.studysprint.api.web;

import com.studysprint.api.dto.auth.LoginUserDto;
import com.studysprint.api.dto.auth.RegisterUserDto;
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
}
