package com.ioet.bpm.skills.boundaries;

import com.ioet.bpm.skills.domain.Skill;
import com.ioet.bpm.skills.repositories.SkillRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class SkillControllerTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillController skillController;

    @Test
    public void skillsAreListedUsingTheRepository() throws Exception {

        ResponseEntity<Iterable> skills =  skillController.findSkills();

        Assert.assertEquals(HttpStatus.OK, skills.getStatusCode());
        Mockito.verify(skillRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void whenASkillIsCreatedTheNewSkillIsReturned() throws Exception {
        Skill skillToCreate= Mockito.mock(Skill.class);
        Skill skillCreated= Mockito.mock(Skill.class);
        Mockito.when(skillRepository.save(skillToCreate)).thenReturn(skillCreated);

        ResponseEntity<Skill> skillCreatedResponse =  skillController.create(skillToCreate);

        Assert.assertEquals(skillCreated, skillCreatedResponse.getBody());
        Assert.assertEquals(HttpStatus.CREATED, skillCreatedResponse.getStatusCode());
        Mockito.verify(skillRepository, Mockito.times(1)).save(skillToCreate);
    }
}
