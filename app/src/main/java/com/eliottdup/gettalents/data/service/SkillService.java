package com.eliottdup.gettalents.data.service;

import com.eliottdup.gettalents.model.Skill;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SkillService {

    @GET("skills")
    Call<List<Skill>> getAllSkills();

    @GET("skills/{id}")
    Call<Skill> getSkillById(@Path("id") String id);

}
