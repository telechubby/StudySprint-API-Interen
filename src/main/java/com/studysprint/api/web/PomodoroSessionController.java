package com.studysprint.api.web;

import com.studysprint.api.service.auth.AuthenticationService;
import com.studysprint.api.service.logic.PomodoroSessionTemplateService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pomodoro/session")
public class PomodoroSessionController {
    private final PomodoroSessionTemplateService pomodoroSessionTemplateService;
    private final AuthenticationService authenticationService;

    public PomodoroSessionController(PomodoroSessionTemplateService pomodoroSessionTemplateService, AuthenticationService authenticationService) {
        this.pomodoroSessionTemplateService = pomodoroSessionTemplateService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<Object> getCurrentSession(@RequestHeader("Authorization") String authorizationHeader)
    {
        return new ResponseEntity<>("Work in Progress", HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<Object> getPastSessions(@RequestHeader("Authorization") String authorizationHeader)
    {
        return new ResponseEntity<>("Work in Progress", HttpStatus.OK);
    }

    @GetMapping("/history/{sessionId}")
    public ResponseEntity<Object> getPastSession(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long sessionId)
    {
        return new ResponseEntity<>("Work in Progress", HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createSession(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Long templateId)
    {
        return new ResponseEntity<>("Work in Progress", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateSession(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Long sessionId, @RequestBody Long templateId)
    {
        return new ResponseEntity<>("Work in Progress", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{sessionId}")
    public ResponseEntity<Object> deleteSession(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long sessionId)
    {
        return new ResponseEntity<>("Work in Progress", HttpStatus.OK);
    }

    @PutMapping("/members/add")
    public ResponseEntity<Object> addUserToCurrentSession(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Long sessionId, @RequestBody Long userId)
    {
        return new ResponseEntity<>("Work in Progress", HttpStatus.OK);
    }

    @PutMapping("/members/delete")
    public ResponseEntity<Object> deleteUserFromCurrentSession(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Long sessionId, @RequestBody Long userId)
    {
        return new ResponseEntity<>("Work in Progress", HttpStatus.OK);
    }
}
