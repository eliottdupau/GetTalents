package com.eliottdup.gettalents.data.repository;

import android.util.Log;

import com.eliottdup.gettalents.data.RetrofitInstance;
import com.eliottdup.gettalents.data.service.ReviewService;
import com.eliottdup.gettalents.model.Review;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewRepository {

    public ReviewRepository() { }

    public MutableLiveData<Review> createReview(Review review) {
        MutableLiveData<Review> reviewMutableLiveData = new MutableLiveData<>();

        ReviewService reviewService = RetrofitInstance.getInstance().create(ReviewService.class);
        Call<Review> call = reviewService.createReview(review);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(@NonNull Call<Review> call, @NonNull Response<Review> response) {
                Log.d("HTTP 200", "Review Creation -> success");

                reviewMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Review> call, @NonNull Throwable t) {
                Log.e("HTTP", "Review Creation -> fail");
            }
        });

        return reviewMutableLiveData;
    }

    public MutableLiveData<Review> getReviewById(int id) {
        MutableLiveData<Review> reviewMutableLiveData = new MutableLiveData<>();

        ReviewService reviewService = RetrofitInstance.getInstance().create(ReviewService.class);
        Call<Review> call = reviewService.getReviewById(id);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(@NonNull Call<Review> call, @NonNull Response<Review> response) {
                Log.d("HTTP 200", "Get User by Id -> success");
                reviewMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Review> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "User Not Found");
            }
        });

        return reviewMutableLiveData;
    }

    public MutableLiveData<List<Review>> getAllReviews() {
        MutableLiveData<List<Review>> reviewsMutableLiveData = new MutableLiveData<>();

        ReviewService reviewService = RetrofitInstance.getInstance().create(ReviewService.class);
        Call<List<Review>> call = reviewService.getAllReviews();
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(@NonNull Call<List<Review>> call, @NonNull Response<List<Review>> response) {
                Log.d("HTTP 200", "Get Reviews -> success");
                reviewsMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Review>> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "Review Not Found");
            }
        });

        return reviewsMutableLiveData;
    }

    public void updateReview(int id, Review review) {
        ReviewService reviewService = RetrofitInstance.getInstance().create(ReviewService.class);
        Call<Void> call = reviewService.updateReview(id, review);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("HTTP 204", "Update Review -> success");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "Review Not Found");
            }
        });
    }

    public void deleteReview(int id) {
        ReviewService reviewService = RetrofitInstance.getInstance().create(ReviewService.class);
        Call<Void> call = reviewService.deleteReview(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("HTTP 200", "Delete Review -> success");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "Review Not Found");
            }
        });
    }

    public MutableLiveData<List<Review>> getReceivedReviewsForUser(int id) {
        MutableLiveData<List<Review>> reviewsMutableLiveData = new MutableLiveData<>();

        ReviewService reviewService = RetrofitInstance.getInstance().create(ReviewService.class);
        Call<List<Review>> call = reviewService.getReceivedReviewsForUser(id);
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(@NonNull Call<List<Review>> call, @NonNull Response<List<Review>> response) {
                Log.d("HTTP 200", "Get All Reviews -> success");
                reviewsMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Review>> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "Reviews Not Found");
            }
        });

        return reviewsMutableLiveData;
    }

    public MutableLiveData<List<Review>> getPostedReviewsForUser(int id) {
        MutableLiveData<List<Review>> userMutableLiveData = new MutableLiveData<>();

        ReviewService reviewService = RetrofitInstance.getInstance().create(ReviewService.class);
        Call<List<Review>> call = reviewService.getPostedReviewsForUser(id);
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(@NonNull Call<List<Review>> call, @NonNull Response<List<Review>> response) {
                Log.d("HTTP 200", "Get All Reviews -> success");
                userMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Review>> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "Reviews Not Found");
            }
        });

        return userMutableLiveData;
    }
}
