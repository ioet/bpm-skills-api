package com.ioet.bpm.skills.boundaries;

import com.ioet.bpm.skills.domain.Person;
import com.ioet.bpm.skills.domain.Skill;
import com.ioet.bpm.skills.domain.SkillPerson;
import com.ioet.bpm.skills.repositories.CategoryRepository;
import com.ioet.bpm.skills.repositories.SkillPersonRepository;
import com.ioet.bpm.skills.repositories.SkillRepository;
import com.ioet.bpm.skills.services.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/skills")
public class SkillController {

    private final SkillRepository skillRepository;
    private final CategoryRepository categoryRepository;
    private final PersonService personService;
    private final SkillPersonRepository skillPersonRepository;

    @Autowired
    public SkillController(SkillRepository skillRepository, CategoryRepository categoryRepository, PersonService personService, SkillPersonRepository skillPersonRepository) {
        this.skillRepository = skillRepository;
        this.categoryRepository = categoryRepository;
        this.personService = personService;
        this.skillPersonRepository = skillPersonRepository;
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

    @RequestMapping(value = "", method = RequestMethod.GET, params = "skillName")
    public ResponseEntity<List> getName(String skillName) {
        List<Skill> skillWithNameCoincidences = skillRepository.findByNameContaining(skillName);
        return new ResponseEntity<>(skillWithNameCoincidences, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a new skill", response = Skill.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Skill successfully created")
    })
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> createSkill(@RequestBody Skill skill) {
        if (categoryRepository.findById(skill.getCategoryId()).isPresent()) {
            Skill skillCreated = skillRepository.save(skill);
            return new ResponseEntity<>(skillCreated, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("categoryId not found", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ApiOperation(value = "Delete a skill")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Skill successfully deleted"),
            @ApiResponse(code = 404, message = "The skill to delete was not found")
    })

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<?> deleteSkill(@PathVariable(value = "id") String skillId) {
        Optional<Skill> skill = skillRepository.findById(skillId);
        if (skill.isPresent()) {
            skillRepository.delete(skill.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Update a skill", response = Skill.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Skill successfully updated"),
            @ApiResponse(code = 404, message = "The skill to update was not found")
    })
    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<?> updateSkill(@PathVariable(value = "id") String skillId,
                                         @Valid @RequestBody Skill skillToUpdate) {

        Optional<Skill> skillFound = skillRepository.findById(skillId);

        if (skillFound.isPresent()) {
            skillToUpdate.setId(skillFound.get().getId());
            Skill updatedSkill = skillRepository.save(skillToUpdate);
            return new ResponseEntity<>(updatedSkill, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Save people and skill id", response = Skill.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Skill successfully saved"),
            @ApiResponse(code = 404, message = "The skill to update was not found")
    })
    @PostMapping(path = "/{skillId}/people", produces = "application/json")
    public ResponseEntity<?> savePeopleId(@RequestParam(value = "personId") String personId,
                                          @PathVariable(value = "skillId") String skillId) throws IOException {
        SkillPerson skillPersonToSave = new SkillPerson(null,personId,personId);
        skillPersonRepository.save(skillPersonToSave);
        //List response = personService.getPersonSkills(skillPersonToSave.getId());
        Iterable<SkillPerson> response = skillPersonRepository.findAllBySkillId(skillPersonToSave.getSkillId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
