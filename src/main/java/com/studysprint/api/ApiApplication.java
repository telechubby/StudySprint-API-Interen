package com.studysprint.api;

import com.studysprint.api.model.auth.Role;
import com.studysprint.api.model.auth.User;
import com.studysprint.api.model.logic.PomodoroSessionTemplate;
import com.studysprint.api.model.logic.SessionStateType;
import com.studysprint.api.repository.PomodoroSessionTemplateRepository;
import com.studysprint.api.repository.RoleRepository;
import com.studysprint.api.repository.SessionStateTypeRepository;
import com.studysprint.api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, SessionStateTypeRepository sessionStateTypeRepository, PomodoroSessionTemplateRepository pomodoroSessionTemplateRepository, PasswordEncoder encoder){
        return args -> {
            if(roleRepository.findByAuthority("ADMIN").isPresent())
                return;
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            Role userRole = roleRepository.save(new Role("USER"));
            Set<Role> ljupceRoles = new HashSet<>();
            ljupceRoles.add(userRole);
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            User ljupceUser = new User(0L,"Ljupce", "ljupce", encoder.encode("ljupce123"), ljupceRoles, "", new ArrayList<>());
            userRepository.save(ljupceUser);
            User adminUser = new User(0L,"admin", "admin", encoder.encode("admin"), adminRoles, "", new ArrayList<>());
            adminUser = userRepository.save(adminUser);
            if(sessionStateTypeRepository.findByName("NOT_STARTED").isPresent())
                return;
            SessionStateType notStartedState = new SessionStateType(0L, "NOT_STARTED");
            SessionStateType workState = new SessionStateType(0L, "WORK");
            SessionStateType breakState = new SessionStateType(0L, "BREAK");
            SessionStateType endedState = new SessionStateType(0L, "ENDED");
            SessionStateType pausedState = new SessionStateType(0L, "PAUSED");
            sessionStateTypeRepository.save(notStartedState);
            sessionStateTypeRepository.save(workState);
            sessionStateTypeRepository.save(breakState);
            sessionStateTypeRepository.save(endedState);
            sessionStateTypeRepository.save(pausedState);
            PomodoroSessionTemplate defaultTemplate = new PomodoroSessionTemplate(0L, "Default", 20, 5, 4, true, adminUser);
            pomodoroSessionTemplateRepository.save(defaultTemplate);
        };
    }
}
