package com.studysprint.api.service.auth;

import com.studysprint.api.dto.auth.LoginUserDto;
import com.studysprint.api.dto.auth.LoginUserResponseDto;
import com.studysprint.api.dto.auth.RegisterUserDto;
import com.studysprint.api.dto.auth.TokenUserDto;
import com.studysprint.api.model.auth.Role;
import com.studysprint.api.model.auth.User;
import com.studysprint.api.repository.RoleRepository;
import com.studysprint.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public LoginUserResponseDto login(LoginUserDto userForm)
    {
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userForm.getUsername(), userForm.getPassword())
            );
            String token = tokenService.generateJwt(auth);
            return new LoginUserResponseDto(userRepository.findByUsername(userForm.getUsername()).orElse(null),token);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new LoginUserResponseDto();
        }
    }

    public User register(RegisterUserDto userForm)
    {
        Set<Role> roleSet = new HashSet<>();
        Role userRole = roleRepository.findByAuthority("USER").orElseThrow(RuntimeException::new);
        roleSet.add(userRole);
        String encodedPassword = encoder.encode(userForm.getPassword());
        User user = new User(0L, userForm.getName(), userForm.getUsername(), encodedPassword, roleSet,"");
        return userRepository.save(user);
    }

    public String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new RuntimeException("Invalid Authorization header");
    }

    public Role getRoleByName(String name)
    {
        return roleRepository.findByAuthority(name).orElseThrow(RuntimeException::new);
    }

    public User getUserByUsername(String username)
    {
        return userRepository.findByUsername(username).orElse(null);
    }

    public TokenUserDto decodeJwtToken(String token)
    {
        return tokenService.decodeJwt(token);
    }
}
