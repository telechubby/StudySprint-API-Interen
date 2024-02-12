package com.studysprint.api.web;

import com.studysprint.api.dto.auth.TokenUserDto;
import com.studysprint.api.dto.logic.CreateSessionTemplateDto;
import com.studysprint.api.model.auth.User;
import com.studysprint.api.model.logic.PomodoroSessionTemplate;
import com.studysprint.api.service.auth.AuthenticationService;
import com.studysprint.api.service.logic.PomodoroSessionTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pomodoro/template")
public class PomodoroSessionTemplateController {
    private final PomodoroSessionTemplateService pomodoroSessionService;
    private final AuthenticationService authenticationService;

    public PomodoroSessionTemplateController(PomodoroSessionTemplateService pomodoroSessionTemplateService, AuthenticationService authenticationService) {
        this.pomodoroSessionService = pomodoroSessionTemplateService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllSessionTemplates(@RequestHeader("Authorization") String authorizationHeader)
    {
        String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
        TokenUserDto userDto = authenticationService.decodeJwtToken(token);
        List<PomodoroSessionTemplate> templates = pomodoroSessionService.findAllTemplates(userDto.getUser());
        return new ResponseEntity<>(templates, HttpStatus.OK);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<Object> getSessionTemplateById(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long sessionId)
    {
        String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
        TokenUserDto userDto = authenticationService.decodeJwtToken(token);
        PomodoroSessionTemplate template = pomodoroSessionService.findTemplateById(sessionId, userDto.getUser());
        if(template == null)
            return new ResponseEntity<>("Template does not exist for the logged in user", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(template, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> createOrUpdatePomodoroSessionTemplate(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CreateSessionTemplateDto userForm)
    {
        try{
            if(!userForm.isComplete())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
            User user = authenticationService.decodeJwtToken(token).getUser();
            if(userForm.getId() != null)
            {
                System.out.println(userForm);
                PomodoroSessionTemplate template = pomodoroSessionService.findTemplateById(userForm.getId(), user);
                if(template == null)
                    return new ResponseEntity<>("Template does not exist for the logged in user", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(pomodoroSessionService.createOrUpdateSessionTemplate(userForm, user), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{sessionId}")
    public ResponseEntity<String> deletePomodoroSessionTemplate(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long sessionId)
    {
        try{
            if(sessionId == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            String token = authenticationService.extractTokenFromAuthorizationHeader(authorizationHeader);
            TokenUserDto userDto = authenticationService.decodeJwtToken(token);
            PomodoroSessionTemplate template = pomodoroSessionService.findTemplateById(sessionId, userDto.getUser());
            if(template == null)
                return new ResponseEntity<>("Template does not exist for the logged in user", HttpStatus.UNAUTHORIZED);
            if(pomodoroSessionService.deleteSessionTemplate(sessionId))
                return new ResponseEntity<>("Session deleted", HttpStatus.OK);
            else
                return new ResponseEntity<>("Error deleting session", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
