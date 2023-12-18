package com.studysprint.api.service.logic;

import com.studysprint.api.dto.logic.CreateSessionTemplateDto;
import com.studysprint.api.model.auth.User;
import com.studysprint.api.model.logic.PomodoroSessionTemplate;
import com.studysprint.api.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PomodoroSessionTemplateService {
    private final PomodoroSessionTemplateRepository sessionTemplateRepository;

    public PomodoroSessionTemplateService(PomodoroSessionTemplateRepository sessionTemplateRepository) {
        this.sessionTemplateRepository = sessionTemplateRepository;
    }

    public PomodoroSessionTemplate findTemplateById(Long sessionId, User user)
    {
        try{
            return sessionTemplateRepository.findByIdAndCreatedByOrIsPublicTrue(sessionId, user).orElse(null);
        }
        catch (Exception e){
            return null;
        }
    }

    public List<PomodoroSessionTemplate> findAllTemplates(User user)
    {
        try{
            return sessionTemplateRepository.findByCreatedByOrIsPublicTrue(user);
        }
        catch (Exception e){
            return null;
        }
    }

    public PomodoroSessionTemplate createOrUpdateSessionTemplate(CreateSessionTemplateDto userForm, User user)
    {
        try{
            if(userForm.getId() == null)
            {
                PomodoroSessionTemplate sessionTemplate = new PomodoroSessionTemplate(0L, userForm.getName(), userForm.getWorkMinutes(), userForm.getBreakMinutes(), userForm.getNumberOfCycles(), userForm.getIsPublic(), user);
                return sessionTemplateRepository.save(sessionTemplate);
            }
            PomodoroSessionTemplate sessionTemplate = sessionTemplateRepository.findByIdAndCreatedByOrIsPublicTrue(userForm.getId(), user).orElse(null);
            if(sessionTemplate == null)
                return null;
            sessionTemplate.setName(userForm.getName());
            sessionTemplate.setWorkMinutes(userForm.getWorkMinutes());
            sessionTemplate.setBreakMinutes(userForm.getBreakMinutes());
            sessionTemplate.setNumberOfCycles(userForm.getNumberOfCycles());
            sessionTemplate.setIsPublic(userForm.getIsPublic());
            return sessionTemplateRepository.save(sessionTemplate);
        }
        catch (Exception e){
            return null;
        }
    }

    public boolean deleteSessionTemplate(Long sessionId)
    {
        try{
            sessionTemplateRepository.deleteById(sessionId);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
