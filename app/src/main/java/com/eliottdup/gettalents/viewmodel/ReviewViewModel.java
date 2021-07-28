package com.eliottdup.gettalents.viewmodel;

import android.app.Application;

import com.eliottdup.gettalents.data.ReviewRepository;
import com.eliottdup.gettalents.model.Review;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ReviewViewModel extends AndroidViewModel {
    private ReviewRepository reviewRepository;

    private MutableLiveData<Review> review;

    public ReviewViewModel(@NonNull Application application) {
        super(application);

        reviewRepository = new ReviewRepository();
    }

    public LiveData<Review> getReview() {
        if (review == null) {
            review = new MutableLiveData<>();

            review.setValue(new Review(UUID.randomUUID().toString()));
        }

        return review;
    }

    public void setReview(Review review) {
        this.review.setValue(review);
    }

    public void createReview(Review review) {
        this.review = reviewRepository.createReview(review);
    }
}
