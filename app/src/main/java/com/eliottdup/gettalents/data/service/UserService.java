package com.eliottdup.gettalents.data.service;

import com.eliottdup.gettalents.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @POST("users")
    Call<Void> createUser(@Body User user);

    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("users/{id}")
    Call<User> getUserById(@Path("id") String id);

    @PATCH("users/{id}")
    Call<Void> updateUser(@Path("id") String id,
                          @Body User user);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") String id);
}
