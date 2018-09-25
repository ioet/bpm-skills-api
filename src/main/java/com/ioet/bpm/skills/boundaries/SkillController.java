package com.ioet.bpm.skills.boundaries;

import com.ioet.bpm.skills.domain.Skill;
import com.ioet.bpm.skills.exception.SkillNotFoundException;
import com.ioet.bpm.skills.repositories.SkillRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/skills")
public class SkillController {

    private final SkillRepository skillRepository;

    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable> findSkills() {
        Iterable<Skill> skills = this.skillRepository.findAll();
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Skill getSkill(@PathVariable(value = "id") String skillId) {
        return skillRepository.findById(skillId)
                .orElseThrow(() -> new SkillNotFoundException("Skill", "id", skillId));
    }

    @PostMapping
    public ResponseEntity<Skill> create(@RequestBody Skill skill) {
        Skill skillCreated = skillRepository.save(skill);
        return new ResponseEntity<>(skillCreated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") String skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new SkillNotFoundException("Skill", "id", skillId));

        skillRepository.delete(skill);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public Skill updateSkill(@PathVariable(value = "id") String skillId, @Valid @RequestBody Skill skillDetails) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new SkillNotFoundException("Skill", "id", skillId));

        skill.setName(skillDetails.getName());
        skill.setDescription(skillDetails.getDescription());

        return skillRepository.save(skill);
    }
}
