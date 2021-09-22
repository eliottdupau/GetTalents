package com.eliottdup.gettalents.data;

import com.eliottdup.gettalents.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("users/{id}")
    Call<User> getUserById(@Path("id") String id);

    @GET("users/category/{id}")
    Call<List<User>> getUsersByCategoryId(@Path("id") String id);

    @GET("users/skill/{id}")
    Call<List<User>> getUsersBySkillId(@Path("id") String id);

}
