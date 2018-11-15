package com.ioet.bpm.skills.boundaries;

import com.ioet.bpm.skills.domain.Skill;
import com.ioet.bpm.skills.repositories.SkillRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/skills")
public class SkillController {

    private final SkillRepository skillRepository;

    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @ApiOperation(value = "Return a list of all skills", response = Skill.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Skills successfully returned")
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<Iterable> getAllSkills() {
        Iterable<Skill> skills = this.skillRepository.findAll();
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @ApiOperation(value = "Return one skill", response = Skill.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Skill successfully returned")
    })
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Skill> getSkill(@PathVariable(value = "id") String skillId) {
        Optional<Skill> skillOptional = skillRepository.findById(skillId);
        return skillOptional.map(
                skill -> new ResponseEntity<>(skill, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/")
    public ResponseEntity<List>getName(String skillName){
        List<Skill> skillWithNameCoincidences = skillRepository.findByNameContaining(skillName);
        return  new ResponseEntity<>(skillWithNameCoincidences,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        Skill skillCreated = skillRepository.save(skill);
        return new ResponseEntity<>(skillCreated, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete a skill")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Skill successfully deleted"),
            @ApiResponse(code = 404, message = "The skill to delete was not found")
    })

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<?> deleteSkill(@PathVariable(value = "id") String skillId) {
        Optional<Skill> skill = skillRepository.findById(skillId);
        if (!skill.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        skillRepository.delete(skill.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Update a skill", response = Skill.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Skill successfully updated"),
            @ApiResponse(code = 404, message = "The skill to update was not found")
    })
    @PutMapping(path = "/{id}", produces = "application/json")
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
