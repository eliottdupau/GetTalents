package com.eliottdup.gettalents.viewmodel;

import android.app.Application;

import com.eliottdup.gettalents.data.repository.ReviewRepository;
import com.eliottdup.gettalents.data.repository.UserRepository;
import com.eliottdup.gettalents.model.Review;
import com.eliottdup.gettalents.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class ReviewListViewModel extends AndroidViewModel {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public MutableLiveData<List<Review>> reviewList;
    public MutableLiveData<User> user;

    public ReviewListViewModel(@NonNull Application application) {
        super(application);

        reviewRepository = new ReviewRepository();
        userRepository = new UserRepository();
    }

    public void getReceivedReviewsForUser(int userId) {
        this.reviewList = reviewRepository.getReceivedReviewsForUser(userId);
    }

    public void getLoggedUser() {
        // Todo() : Changer avec la méthode de réccupération du user logged
        this.user = userRepository.getUserById(1);
    }
}
