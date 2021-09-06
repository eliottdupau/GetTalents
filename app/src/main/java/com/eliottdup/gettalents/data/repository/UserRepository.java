package com.eliottdup.gettalents.data.repository;

import android.util.Log;

import com.eliottdup.gettalents.data.RetrofitInstance;
import com.eliottdup.gettalents.data.service.UserService;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.Picture;
import com.eliottdup.gettalents.model.Review;
import com.eliottdup.gettalents.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    public UserRepository() { }

    public void createUser(User user) {
        UserService userService = RetrofitInstance.getInstance().create(UserService.class);
        Call<Void> call = userService.createUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("HTTP 201", "User Creation -> success");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("HTTP", "User Creation -> fail");
            }
        });
    }

    public MutableLiveData<User> getUserById(int id) {
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

        UserService userService = RetrofitInstance.getInstance().create(UserService.class);
        Call<User> call = userService.getUserById(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                Log.d("HTTP 200", "Get User by Id -> success");
                userMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "User Not Found");
            }
        });

        return userMutableLiveData;
    }

    public MutableLiveData<List<User>> getAllUsers() {
        MutableLiveData<List<User>> usersMutableLiveData = new MutableLiveData<>();

        UserService userService = RetrofitInstance.getInstance().create(UserService.class);
        Call<List<User>> call = userService.getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                Log.d("HTTP 200", "Get User by Id -> success");
                usersMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "User Not Found");
            }
        });

        return usersMutableLiveData;
    }

    public void updateUser(int id, User user) {
        UserService userService = RetrofitInstance.getInstance().create(UserService.class);
        Call<Void> call = userService.updateUser(id, user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("HTTP 204", "Update User -> success");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "User Not Found");
            }
        });
    }

    public void deleteUser(int id) {
        UserService userService = RetrofitInstance.getInstance().create(UserService.class);
        Call<Void> call = userService.deleteUser(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("HTTP 200", "Delete User -> success");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "User Not Found");
            }
        });
    }
}
