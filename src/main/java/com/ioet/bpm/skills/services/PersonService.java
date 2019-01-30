package com.ioet.bpm.skills.services;

import com.ioet.bpm.skills.domain.Person;
import com.ioet.bpm.skills.domain.Skill;
import com.ioet.bpm.skills.domain.SkillPerson;
import com.ioet.bpm.skills.repositories.RetrofitRepository;
import com.ioet.bpm.skills.repositories.SkillPersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PersonService {

    private RetrofitRepository retrofitRepository;
    private SkillPersonRepository skillPersonRepository;

    public PersonService() {
        String people_api_base_url = System.getenv("PEOPLE_API_BASE_URL");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(people_api_base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitRepository = retrofit.create(RetrofitRepository.class);
    }

    public List getPersonSkills(String skillId) throws IOException {

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

    public List<Person> getAllPersons() throws IOException {
        Call<List<Person>> retrofitCall = retrofitRepository.getPeople();
        Response<List<Person>> response = retrofitCall.execute();
        return response.body();

    }

    public List<Skill> getAllSkills() throws IOException {
        Call<List<Skill>> retrofitCall = retrofitRepository.getSkills();
        Response<List<Skill>> response = retrofitCall.execute();
        return response.body();

    }
}
