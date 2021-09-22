package com.eliottdup.gettalents.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.eliottdup.gettalents.model.Skill;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillRepository {
    private final List<Skill> skillList;

    public SkillRepository() {
        this.skillList = new ArrayList<>();
    }

    public MutableLiveData<List<Skill>> getSkills() {
        MutableLiveData<List<Skill>> skillsMutableLiveData = new MutableLiveData<>();

        SkillService skillService = RetrofitInstance.getInstance().create(SkillService.class);
        Call<List<Skill>> call = skillService.getAllSkills();
        call.enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(@NonNull Call<List<Skill>> call, @NonNull Response<List<Skill>> response) {
                Log.d("HTTP 200", "Get All Skills -> success");
                skillsMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Skill>> call, @NonNull Throwable t) {
                Log.d("HTTP 404", "Skill Not Found");
            }
        });

        return skillsMutableLiveData;
    }

    public MutableLiveData<Skill> getSkillById(String id) {
        MutableLiveData<Skill> skillMutableLiveData = new MutableLiveData<>();

        SkillService skillService = RetrofitInstance.getInstance().create(SkillService.class);
        Call<Skill> call = skillService.getSkillById(id);
        call.enqueue(new Callback<Skill>() {
            @Override
            public void onResponse(@NonNull Call<Skill> call, @NonNull Response<Skill> response) {
                Log.d("HTTP 200", "Get Skill by id -> success");
                skillMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Skill> call, @NonNull Throwable t) {
                Log.d("HTTP 404", "Skill Not Found");
            }
        });

        return skillMutableLiveData;
    }

}
