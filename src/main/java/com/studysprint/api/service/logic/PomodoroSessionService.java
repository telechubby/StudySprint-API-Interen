package com.studysprint.api.service.logic;

import com.studysprint.api.model.auth.User;
import com.studysprint.api.model.logic.PomodoroSession;
import com.studysprint.api.model.logic.PomodoroSessionState;
import com.studysprint.api.model.logic.PomodoroSessionTemplate;
import com.studysprint.api.model.logic.SessionStateType;
import com.studysprint.api.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PomodoroSessionService {
    private final PomodoroSessionRepository sessionRepository;
    private final PomodoroSessionStateRepository sessionStateRepository;
    private final PomodoroSessionTemplateRepository sessionTemplateRepository;
    private final SessionStateTypeRepository sessionStateTypeRepository;
    private final UserRepository userRepository;

    public PomodoroSessionService(PomodoroSessionRepository sessionRepository, PomodoroSessionStateRepository sessionStateRepository, PomodoroSessionTemplateRepository sessionTemplateRepository, SessionStateTypeRepository sessionStateTypeRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.sessionStateRepository = sessionStateRepository;
        this.sessionTemplateRepository = sessionTemplateRepository;
        this.sessionStateTypeRepository = sessionStateTypeRepository;
        this.userRepository = userRepository;
    }

    public PomodoroSession getCurrentUserSession(User user){
        SessionStateType workState = sessionStateTypeRepository.findByName("WORK").orElseThrow();
        SessionStateType breakState = sessionStateTypeRepository.findByName("BREAK").orElseThrow();
        SessionStateType notStartedState = sessionStateTypeRepository.findByName("NOT_STARTED").orElseThrow();
        List<PomodoroSession> workSession = sessionRepository.findByModeratorAndSessionState_SessionState(user, workState);
        if(workSession.size()>0)
            return workSession.get(0);
        List<PomodoroSession> breakSession = sessionRepository.findByModeratorAndSessionState_SessionState(user, breakState);
        if(breakSession.size()>0)
            return breakSession.get(0);
        List<PomodoroSession> notWorkingSession = sessionRepository.findByModeratorAndSessionState_SessionState(user, notStartedState);
        if(notWorkingSession.size()>0)
            return notWorkingSession.get(0);
        return null;
    }

    public PomodoroSession getUserPastSessionById(User user, Long sessionId){
        SessionStateType endedState = sessionStateTypeRepository.findByName("ENDED").orElse(null);
        if(endedState == null)
            return null;
        return sessionRepository.findByModeratorAndIdAndSessionState_SessionState(user, sessionId, endedState).orElse(null);
    }

    public List<PomodoroSession> getPastUserSessions(User user){
        SessionStateType endedState = sessionStateTypeRepository.findByName("ENDED").orElseThrow();
        return sessionRepository.findByModeratorAndSessionState_SessionState(user, endedState);
    }

    public PomodoroSession createNewSession(User user, Long templateId)
    {
        PomodoroSession session = getCurrentUserSession(user);
        if(session != null)
            return session;
        SessionStateType notWorkingState = sessionStateTypeRepository.findByName("NOT_STARTED").orElse(null);
        PomodoroSessionTemplate template = sessionTemplateRepository.findById(templateId).orElse(null);
        if(notWorkingState == null || template == null)
            return null;
        PomodoroSessionState sessionState = new PomodoroSessionState(0L, LocalDateTime.now(), LocalDateTime.now(), notWorkingState, 0);
        sessionState = sessionStateRepository.save(sessionState);
        if(sessionState == null)
            return null;
        session = new PomodoroSession(0L, sessionState, template, user, new HashSet<>());
        return sessionRepository.save(session);
    }

    public SessionStateType startCurrentUserSession(User user)
    {
        PomodoroSession session = getCurrentUserSession(user);
        SessionStateType workState = sessionStateTypeRepository.findByName("WORK").orElse(null);
        SessionStateType notWorkingState = sessionStateTypeRepository.findByName("NOT_STARTED").orElse(null);
        if(session == null || workState == null || notWorkingState == null || session.getSessionState() == null || session.getSessionState().getSessionState() != notWorkingState || session.getSessionState().getCycleNumber() != 0)
            return null;
        PomodoroSessionState sessionState = session.getSessionState();
        if(sessionState.getSessionState().equals(workState))
            return workState;
        sessionState.setSessionState(workState);
        sessionState.setLastSessionUpdate(LocalDateTime.now());
        sessionState.setCycleNumber(sessionState.getCycleNumber() + 1);
        sessionState = sessionStateRepository.save(sessionState);
        if(sessionState == null)
            return null;
        session.setSessionState(sessionState);
        session = sessionRepository.save(session);
        if(session == null)
            return null;
        return workState;
    }

    public PomodoroSession endCurrentUserSession(User user)
    {
        PomodoroSession session = getCurrentUserSession(user);
        SessionStateType endedState = sessionStateTypeRepository.findByName("ENDED").orElse(null);
        if(session == null || endedState == null || session.getSessionState() == null)
            return null;
        PomodoroSessionState sessionState = session.getSessionState();
        sessionState.setSessionState(endedState);
        sessionState.setLastSessionUpdate(LocalDateTime.now());
        sessionState = sessionStateRepository.save(sessionState);
        if(sessionState == null)
            return null;
        session.setSessionState(sessionState);
        return sessionRepository.save(session);
    }

    public PomodoroSession updateCurrentUserSession(User user)
    {
        PomodoroSession session = getCurrentUserSession(user);
        SessionStateType notStartedState = sessionStateTypeRepository.findByName("NOT_STARTED").orElse(null);
        SessionStateType workState = sessionStateTypeRepository.findByName("WORK").orElse(null);
        SessionStateType breakState = sessionStateTypeRepository.findByName("BREAK").orElse(null);
        SessionStateType endedState = sessionStateTypeRepository.findByName("ENDED").orElse(null);
        if(session == null || notStartedState == null || workState == null || breakState == null || endedState == null || session.getSessionState() == null)
            return null;
        PomodoroSessionState sessionState = session.getSessionState();
        if(sessionState.getSessionState().equals(workState))
        {
            if(sessionState.getLastSessionUpdate().plusMinutes(session.getSessionTemplate().getWorkMinutes()).isBefore(LocalDateTime.now()))
            {
                sessionState.setSessionState(breakState);
                sessionState.setLastSessionUpdate(LocalDateTime.now());
            }
        }
        else if(sessionState.getSessionState().equals(breakState))
        {
            if(sessionState.getLastSessionUpdate().plusMinutes(session.getSessionTemplate().getBreakMinutes()).isBefore(LocalDateTime.now()))
            {
                if(sessionState.getCycleNumber().equals(session.getSessionTemplate().getNumberOfCycles()))
                {
                    sessionState.setSessionState(endedState);
                    sessionState.setLastSessionUpdate(LocalDateTime.now());
                }
                else{
                    sessionState.setSessionState(workState);
                    sessionState.setCycleNumber(sessionState.getCycleNumber() + 1);
                    sessionState.setLastSessionUpdate(LocalDateTime.now());
                }
            }
        }
        sessionState = sessionStateRepository.save(sessionState);
        if(sessionState == null)
            return null;
        session.setSessionState(sessionState);
        return sessionRepository.save(session);
    }

    public PomodoroSession addUserToCurrentSession(User moderator, Long userId)
    {
        User toBeAdded = userRepository.findById(userId).orElse(null);
        PomodoroSession session = getCurrentUserSession(moderator);
        if(toBeAdded == null || session == null)
            return null;
        Set<User> members = session.getMembers();
        members.add(toBeAdded);
        session.setMembers(members);
        return sessionRepository.save(session);
    }

    public PomodoroSession deleteUserFromCurrentSession(User moderator, Long userId)
    {
        User toBeAdded = userRepository.findById(userId).orElse(null);
        PomodoroSession session = getCurrentUserSession(moderator);
        if(toBeAdded == null || session == null)
            return null;
        Set<User> members = session.getMembers();
        members.remove(toBeAdded);
        session.setMembers(members);
        return sessionRepository.save(session);
    }
}
