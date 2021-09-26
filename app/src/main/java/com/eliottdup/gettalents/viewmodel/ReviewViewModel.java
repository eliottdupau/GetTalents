package com.eliottdup.gettalents.viewmodel;

import android.app.Application;

import com.eliottdup.gettalents.data.repository.ReviewRepository;
import com.eliottdup.gettalents.data.repository.UserRepository;
import com.eliottdup.gettalents.model.Review;
import com.eliottdup.gettalents.model.User;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ReviewViewModel extends AndroidViewModel {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    private MutableLiveData<Review> review;

    public ReviewViewModel(@NonNull Application application) {
        super(application);

        reviewRepository = new ReviewRepository();
        userRepository = new UserRepository();
    }

    public LiveData<User> getLoggedUser(String id) {
        return userRepository.getUserById(id);
    }

    public LiveData<User> getUserById(String userId) {
        return userRepository.getUserById(userId);
    }

    public LiveData<Review> createReview(Review review) {
        return reviewRepository.createReview(review);
    }

    public LiveData<Review> getReview() {
        if (review == null) {
            review = new MutableLiveData<>();

            review.setValue(new Review());
        }

        return review;
    }

    public void setReview(Review review) {
        this.review.setValue(review);
    }
}
