package com.eliottdup.gettalents.data.service;

import com.eliottdup.gettalents.model.Picture;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PictureService {

    @POST("pictures")
    Call<Void> createPicture(@Body Picture picture);

    @GET("pictures")
    Call<List<Picture>> getAllPictures();

    @GET("pictures/{id}")
    Call<Picture> getPictureById(@Path("id") int id);

    @PATCH("pictures/{id}")
    Call<Void> updatePicture(@Path("id") int id,
                             @Body Picture picture);

    @DELETE("pictures/{id}")
    Call<Void> deletePicture(@Path("id") int id);
}
