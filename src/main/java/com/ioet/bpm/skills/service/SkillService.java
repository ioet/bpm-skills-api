package com.ioet.bpm.skills.service;

import com.ioet.bpm.skills.domain.Skill;
import com.ioet.bpm.skills.domain.exception.EntityNotFoundException;
import com.ioet.bpm.skills.repositories.SkillRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;

@Component
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }
    public Skill save(String skillId,
               @Valid @RequestBody Skill skillDetails) {

        Optional<Skill> skillFromDB = skillRepository.findById(skillId);
        if (!skillFromDB.isPresent()) {
            throw new EntityNotFoundException("Error getting Skill with id=" + skillId);
        }
        skillDetails.setId(skillFromDB.get().getId());
        skillRepository.save(skillDetails);
        return skillDetails;
    }
}
