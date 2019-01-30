package com.ioet.bpm.skills.repositories;

import com.ioet.bpm.skills.domain.Person;
import com.ioet.bpm.skills.domain.Skill;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface RetrofitRepository {

    @GET("people/{personId}")
    Call<Person> getPeopleById(@Path("personId") String personId);

    @GET("people")
    Call<List<Person>> getPeople();

    @GET("skills")
    Call<List<Skill>> getSkills();
}

