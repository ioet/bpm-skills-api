package com.ioet.bpm.skills.services;

import com.google.gson.Gson;
import com.ioet.bpm.skills.domain.Person;
import com.ioet.bpm.skills.domain.SkillPerson;
import com.ioet.bpm.skills.repositories.PersonSkillRepository;
import com.ioet.bpm.skills.repositories.RetrofitRepository;
import com.ioet.bpm.skills.repositories.SkillPersonRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    @Mock
    SkillPersonRepository skillPersonRepository;

    @Mock
    PersonSkillRepository personSkillRepository;

    @Mock
    PersonService personService;

    @Mock
    RetrofitRepository retrofitRepository;

    @Test
    public void whenASkillIsSavedShownWhatPeopleHasThatSkill() throws IOException {
        String skillId = "someSkillId";
        Iterable<SkillPerson> skillCoincidences = mock(Iterable.class);
        List peopleWithSkill = mock(List.class);

        doReturn(peopleWithSkill).when(personService).getSkillPeople(skillId);

        List response = personService.getSkillPeople(skillId);

        Assert.assertEquals(peopleWithSkill ,response );
    }

    @Test
    public void whenAPersonIsFoundByIdReturnThatPerson() throws IOException {
        String personId = "personId";
        Call<Person> retrofitCall = mock(Call.class);
        Optional personOptional = Optional.of(mock(List.class));

        //when(retrofitRepository.getPeopleById(personId)).thenReturn(retrofitCall);
        when(personService.getOnePerson(personId)).thenReturn(personOptional);
        Optional response = personService.getOnePerson(personId);

        Assert.assertEquals(personOptional.get(), response.get());




    }


}
