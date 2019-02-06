package com.ioet.bpm.skills.repositories;

import com.ioet.bpm.skills.domain.Person;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitRepository {

    @GET("people/{personId}")
    Call<Person> getPeopleById(@Path("personId") String personId);

}

