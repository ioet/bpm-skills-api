package com.ioet.bpm.skills.services;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ioet.bpm.skills.domain.Person;
import com.ioet.bpm.skills.domain.PersonSkill;
import com.ioet.bpm.skills.domain.Skill;
import com.ioet.bpm.skills.domain.SkillPerson;
import com.ioet.bpm.skills.repositories.PersonSkillRepository;
import com.ioet.bpm.skills.repositories.RetrofitRepository;
import com.ioet.bpm.skills.repositories.SkillPersonRepository;
import com.ioet.bpm.skills.repositories.SkillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PersonService {

    private RetrofitRepository retrofitRepository;
    @Autowired
    private SkillPersonRepository skillPersonRepository;
    @Autowired
    private PersonSkillRepository personSkillRepository;
    @Autowired
    private SkillRepository skillRepository;

    public PersonService() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        String people_api_base_url = System.getenv("PEOPLE_API_BASE_URL");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(people_api_base_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitRepository = retrofit.create(RetrofitRepository.class);
    }

    public List getSkillPeople(String skillId) throws IOException {


        Iterable<SkillPerson> personList = skillPersonRepository.findAllBySkillId(skillId);
        List personObjects = new ArrayList();

        personList.forEach(skillPerson ->
                {
                    Call<Person> retrofitCall = retrofitRepository.getPeopleById(skillPerson.getPersonId());
                    Response<Person> response = null;
                    try {
                        response = retrofitCall.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.info(e.getMessage());
                    }
                    personObjects.add(response.body());

                }
        );

        return personObjects;
    }

    public List getPersonSkills(String personId) throws IOException {
        Iterable<PersonSkill> skillList = personSkillRepository.findAllByPersonId(personId);
        List skillsFound = new ArrayList();
        skillList.forEach(personSkill -> {
            skillsFound.add(skillRepository.findById(personSkill.getSkillId()));
        });

        return skillsFound;
    }

    public Optional<?> getOnePerson(String personId) throws IOException {

        Call<Person> retrofitCall = retrofitRepository.getPeopleById(personId);
        Response<Person> response = retrofitCall.execute();
        return Optional.of(response.body());
    }
}
