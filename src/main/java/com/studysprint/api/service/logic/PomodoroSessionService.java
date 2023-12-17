package com.studysprint.api.service.logic;

import com.studysprint.api.repository.PomodoroSessionRepository;
import com.studysprint.api.repository.PomodoroSessionStateRepository;
import com.studysprint.api.repository.PomodoroSessionTemplateRepository;
import org.springframework.stereotype.Service;

@Service
public class PomodoroSessionService {
    private final PomodoroSessionRepository pomodoroSessionRepository;
    private final PomodoroSessionStateRepository pomodoroSessionStateRepository;
    private final PomodoroSessionTemplateRepository pomodoroSessionTemplateRepository;

    public PomodoroSessionService(PomodoroSessionRepository pomodoroSessionRepository, PomodoroSessionStateRepository pomodoroSessionStateRepository, PomodoroSessionTemplateRepository pomodoroSessionTemplateRepository) {
        this.pomodoroSessionRepository = pomodoroSessionRepository;
        this.pomodoroSessionStateRepository = pomodoroSessionStateRepository;
        this.pomodoroSessionTemplateRepository = pomodoroSessionTemplateRepository;
    }
}
