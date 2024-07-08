package com.studysprint.api.service.auth;

import com.studysprint.api.dto.auth.TokenUserDto;
import com.studysprint.api.model.auth.User;
import com.studysprint.api.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;

    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, UserRepository userRepository) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.userRepository = userRepository;
    }

    public String generateJwt(Authentication authentication){
        Instant now  = Instant.now();

        String scope = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(24*30, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("roles", scope)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
    public TokenUserDto decodeJwt(String token)
    {
        Jwt jwt =  jwtDecoder.decode(token);
        String username = (String) jwt.getClaims().get("sub");
        User user = userRepository.findByUsername(username).orElse(null);
        return new TokenUserDto(user, (Instant) jwt.getClaims().get("iat"), (Instant) jwt.getClaims().get("exp"));
    }
}
