package com.eliottdup.gettalents.data.service;

import com.eliottdup.gettalents.model.Review;
import com.eliottdup.gettalents.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewService {

    @POST("reviews")
    Call<Review> createReview(@Body Review review);

    @GET("reviews")
    Call<List<Review>> getAllReviews();

    @GET("reviews/{id}")
    Call<Review> getReviewById(@Path("id") int id);

    @PATCH("reviews/{id}")
    Call<Void> updateReview(@Path("id") int id,
                          @Body Review review);

    @DELETE("reviews/{id}")
    Call<Void> deleteReview(@Path("id") int id);

    @GET("reviews/received/{userId}")
    Call<List<Review>> getReceivedReviewsForUser(@Path("userId") int id);

    @GET("reviews/posted/{userId}")
    Call<List<Review>> getPostedReviewsForUser(@Path("userId") int id);

}
