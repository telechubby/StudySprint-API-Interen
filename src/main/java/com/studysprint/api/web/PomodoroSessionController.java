package com.studysprint.api.web;

import com.studysprint.api.model.auth.User;
import com.studysprint.api.model.logic.PomodoroSession;
import com.studysprint.api.model.logic.PomodoroSessionTemplate;
import com.studysprint.api.model.logic.SessionStateType;
import com.studysprint.api.service.auth.AuthenticationService;
import com.studysprint.api.service.logic.PomodoroSessionService;
import com.studysprint.api.service.logic.PomodoroSessionTemplateService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pomodoro/session")
public class PomodoroSessionController {
    private final PomodoroSessionService pomodoroSessionService;
    private final PomodoroSessionTemplateService pomodoroSessionTemplateService;
    private final AuthenticationService authenticationService;

    public PomodoroSessionController(PomodoroSessionService pomodoroSessionService, PomodoroSessionTemplateService pomodoroSessionTemplateService, AuthenticationService authenticationService) {
        this.pomodoroSessionService = pomodoroSessionService;
        this.pomodoroSessionTemplateService = pomodoroSessionTemplateService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<Object> getCurrentSession(@RequestHeader("Authorization") String authorizationHeader)
    {
        try{
            String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
            User user = authenticationService.decodeJwtToken(token).getUser();
            PomodoroSession session = pomodoroSessionService.getCurrentUserSession(user);
            return new ResponseEntity<>(session, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/history")
    public ResponseEntity<Object> getPastSessions(@RequestHeader("Authorization") String authorizationHeader)
    {
        try{
            String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
            User user = authenticationService.decodeJwtToken(token).getUser();
            List<PomodoroSession> sessions = pomodoroSessionService.getPastUserSessions(user);
            return new ResponseEntity<>(sessions, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/history/{sessionId}")
    public ResponseEntity<Object> getPastSession(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long sessionId)
    {
        try{
            String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
            User user = authenticationService.decodeJwtToken(token).getUser();
            PomodoroSession session = pomodoroSessionService.getUserPastSessionById(user, sessionId);
            if(session == null)
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(session, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createSession(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Long templateId)
    {
        System.out.println(templateId);
        String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
        User user = authenticationService.decodeJwtToken(token).getUser();
        PomodoroSessionTemplate template = pomodoroSessionTemplateService.findTemplateById(templateId, user);
        if(template == null)
            return new ResponseEntity<>("Invalid template ID", HttpStatus.BAD_REQUEST);
        PomodoroSession session = pomodoroSessionService.createNewSession(user, templateId);
        if(session == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        System.out.println(session.getSessionState().getSessionState().getName());
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    @PostMapping("/start")
    public ResponseEntity<Object> startSession(@RequestHeader("Authorization") String authorizationHeader)
    {
        String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
        User user = authenticationService.decodeJwtToken(token).getUser();
        SessionStateType currentState = pomodoroSessionService.startCurrentUserSession(user);
        if(currentState == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(currentState, HttpStatus.OK);
    }

    @PostMapping("/end")
    public ResponseEntity<Object> endSession(@RequestHeader("Authorization") String authorizationHeader)
    {
        String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
        User user = authenticationService.decodeJwtToken(token).getUser();
        PomodoroSession endedSession = pomodoroSessionService.endCurrentUserSession(user);
        if(endedSession == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(endedSession, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateSession(@RequestHeader("Authorization") String authorizationHeader)
    {
        String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
        User user = authenticationService.decodeJwtToken(token).getUser();
        PomodoroSession updatedSession = pomodoroSessionService.updateCurrentUserSession(user);
        if(updatedSession == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(updatedSession, HttpStatus.OK);
    }

    @PutMapping("/members/add")
    public ResponseEntity<Object> addUserToCurrentSession(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Long userId)
    {
        String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
        User moderator = authenticationService.decodeJwtToken(token).getUser();
        PomodoroSession session = pomodoroSessionService.addUserToCurrentSession(moderator, userId);
        if(session == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    @PutMapping("/members/delete")
    public ResponseEntity<Object> deleteUserFromCurrentSession(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Long userId)
    {
        String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
        User moderator = authenticationService.decodeJwtToken(token).getUser();
        PomodoroSession session = pomodoroSessionService.deleteUserFromCurrentSession(moderator, userId);
        if(session == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(session, HttpStatus.OK);
    }
}
