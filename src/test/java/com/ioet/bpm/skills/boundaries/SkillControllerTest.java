package com.ioet.bpm.skills.boundaries;

import com.ioet.bpm.skills.domain.*;
import com.ioet.bpm.skills.repositories.CategoryRepository;
import com.ioet.bpm.skills.repositories.PersonSkillRepository;
import com.ioet.bpm.skills.repositories.SkillPersonRepository;
import com.ioet.bpm.skills.repositories.SkillRepository;
import com.ioet.bpm.skills.services.PersonService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SkillControllerTest {

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private SkillController skillController;

    @Mock
    private SkillPersonRepository skillPersonRepository;

    @Mock
    private PersonSkillRepository personSkillRepository;

    @Mock
    private PersonService personService;

    @Test
    public void skillsAreListedUsingTheRepository() throws Exception {

        ResponseEntity<Iterable> skills = skillController.getAllSkills();

        Assert.assertEquals(HttpStatus.OK, skills.getStatusCode());
        Mockito.verify(skillRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void whenASkillIsCreatedTheNewSkillIsReturned() throws Exception {
        Skill skillToCreate = mock(Skill.class);
        Skill personSkillCreated = mock(Skill.class);
        Optional<Category> category = Optional.of(mock(Category.class));
        when(categoryRepository.findById(Mockito.anyString())).thenReturn(category);
        when(skillToCreate.getCategoryId()).thenReturn("1");
        when(skillRepository.save(skillToCreate)).thenReturn(personSkillCreated);

        ResponseEntity<?> skillCreatedResponse = skillController.createSkill(skillToCreate);

        Assert.assertEquals(personSkillCreated, skillCreatedResponse.getBody());
        Assert.assertEquals(HttpStatus.CREATED, skillCreatedResponse.getStatusCode());
        Mockito.verify(skillRepository, Mockito.times(1)).save(skillToCreate);
    }

    @Test
    public void whenASkillWithAnUnknownCategoryWantsToBeCreatedAUnproccessableEntityMesssageIsReturned() throws Exception {
        Skill skillToCreate = mock(Skill.class);

        ResponseEntity<?> skillCreatedResponse = skillController.createSkill(skillToCreate);

        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, skillCreatedResponse.getStatusCode());
    }

    @Test
    public void skillsFoundByNameCoincidences() throws Exception {
        Skill skill = mock(Skill.class);
        String skillName = skill.getName();
        List<Skill> foundNameCoincidences = mock(List.class);
        when(skillRepository.findByNameContaining(skillName)).thenReturn(foundNameCoincidences);

        ResponseEntity<List> nameCoincidencesList = skillController.getName(skillName);

        Assert.assertEquals(HttpStatus.OK, nameCoincidencesList.getStatusCode());
        Mockito.verify(skillRepository, Mockito.times(1)).findByNameContaining(skillName);
    }


    @Test
    public void whenSkillIsAssignedToPersonThenReturnAllPeopleHasThisSkill() throws Exception {
        String skillId = "someSkillId";
        String personId = "somePersonId";
        SkillPerson skillPersonToSave = new SkillPerson(skillId, personId);
        Optional skillFound = Optional.of(mock(Skill.class));
        SkillPerson personSkillCreated = mock(SkillPerson.class);
        List peopleFound = mock(List.class);

        when(skillRepository.findById(skillId)).thenReturn(skillFound);
        when(skillPersonRepository.save(skillPersonToSave)).thenReturn(personSkillCreated);
        when(personService.getSkillPeople(skillPersonToSave.getSkillId())).thenReturn(peopleFound);
        ResponseEntity<?> peopleWithSkill = skillController.savePeopleId(personId, skillId);

        Assert.assertEquals(peopleFound, peopleWithSkill.getBody());
        Assert.assertEquals(HttpStatus.OK, peopleWithSkill.getStatusCode());
        verify(skillRepository).findById(skillId);
        verify(personService).getSkillPeople(skillId);
    }

    @Test
    public void whenSkillIsAssignedToPersonAndNotFoundThenReturn400() throws IOException {
        String skillId = "someSkillId";
        String personId = "somePersonId";

        when(skillRepository.findById(skillId)).thenReturn(Optional.empty());
        ResponseEntity<?> notFoundSkillResponse = skillController.savePeopleId(personId, skillId);

        verify(skillRepository).findById(skillId);
    }

    @Test
    public void whenAPersonIsStoredInToASkillThenReturnAllTheSkillThisPersonHave() throws IOException {
        String skillId = "someSkillId";
        String personId = "somePersonId";
        PersonSkill personSkillToSave = new PersonSkill(personId, skillId);
        Optional personFound = Optional.of(mock(Person.class));
        PersonSkill personSkillCreated = mock(PersonSkill.class);
        List skillsFound = mock(List.class);

        when(personService.getOnePerson(personId)).thenReturn(personFound);
        when(personSkillRepository.save(personSkillToSave)).thenReturn(personSkillCreated);
        when(personService.getPersonSkills(personSkillToSave.getPersonId())).thenReturn(skillsFound);
        ResponseEntity<?> personSkills = skillController.saveSkillId(skillId, personId);

        Assert.assertEquals(skillsFound, personSkills.getBody());
        Assert.assertEquals(HttpStatus.OK, personSkills.getStatusCode());
        verify(personService).getOnePerson(personId);
        verify(personService).getPersonSkills(personId);
    }

    @Test
    public void whenAPersonIsStoredInToASkillAndNotFoundThenReturn400() throws IOException {
        String skillId = "someSkillId";
        String personId = "somePersonId";

        when(personService.getOnePerson(personId)).thenReturn(Optional.empty());
        ResponseEntity<?> notFoundPeopleResponse = skillController.saveSkillId(skillId, personId);

        verify(personService).getOnePerson(personId);
    }
}

