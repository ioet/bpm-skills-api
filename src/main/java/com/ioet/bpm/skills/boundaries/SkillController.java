package com.ioet.bpm.skills.boundaries;

import com.ioet.bpm.skills.domain.Skill;
import com.ioet.bpm.skills.repositories.SkillRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/skills")
public class SkillController {

    private final SkillRepository skillRepository;

    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable> getAllSkills() {
        Iterable<Skill> skills = this.skillRepository.findAll();
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkill(@PathVariable(value = "id") String skillId) {
        Optional<Skill> skillOptional = skillRepository.findById(skillId);
        return skillOptional.map(
                skill -> new ResponseEntity<>(skill, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        Skill skillCreated = skillRepository.save(skill);
        return new ResponseEntity<>(skillCreated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable(value = "id") String skillId) {
        Optional<Skill> skill = skillRepository.findById(skillId);
        if (!skill.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        skillRepository.delete(skill.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable(value = "id") String skillId,
                                             @Valid @RequestBody Skill skillDetails) {

        Optional<Skill> skillOptional = skillRepository.findById(skillId);
        if (!skillOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Skill skill = skillOptional.get();
        skill.setName(skillDetails.getName());
        skill.setDescription(skillDetails.getDescription());
        skillRepository.save(skill);
        return new ResponseEntity<>(skill, HttpStatus.OK);
    }
}
