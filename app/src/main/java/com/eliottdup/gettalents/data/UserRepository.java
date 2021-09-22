package com.eliottdup.gettalents.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.eliottdup.gettalents.model.User;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private final List<User> userList;

    public UserRepository() {
        this.userList = new ArrayList<>();
    }

    public MutableLiveData<List<User>> getUsers() {
        MutableLiveData<List<User>> usersMutableLiveData = new MutableLiveData<>();

        UserService userService = RetrofitInstance.getInstance().create(UserService.class);
        Call<List<User>> call = userService.getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                Log.d("HTTP 200", "Get All Users -> success");
                usersMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.d("HTTP 404", "User Not Found");
            }
        });

        return usersMutableLiveData;
    }

    public MutableLiveData<User> getUserById(String id) {
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

        UserService userService = RetrofitInstance.getInstance().create(UserService.class);
        Call<User> call = userService.getUserById(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                Log.d("HTTP 200", "Get User by id -> success");
                userMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.d("HTTP 404", "User Not Found");
            }
        });

        return userMutableLiveData;
    }

    public MutableLiveData<List<User>> getUsersByCategoryId(String id) {
        MutableLiveData<List<User>> usersMutableLiveData = new MutableLiveData<>();

        UserService userService = RetrofitInstance.getInstance().create(UserService.class);
        Call<List<User>> call = userService.getUsersByCategoryId(id);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                Log.d("HTTP 200", "Get Users By Category -> success");
                usersMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.d("HTTP 404", "User Not Found");
            }
        });

        return usersMutableLiveData;
    }

    public MutableLiveData<List<User>> getUsersBySkillId(String id) {
        MutableLiveData<List<User>> usersMutableLiveData = new MutableLiveData<>();

        UserService userService = RetrofitInstance.getInstance().create(UserService.class);
        Call<List<User>> call = userService.getUsersBySkillId(id);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                Log.d("HTTP 200", "Get Users By Category -> success");
                usersMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.d("HTTP 404", "User Not Found");
            }
        });

        return usersMutableLiveData;
    }

}
