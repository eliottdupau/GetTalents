package com.eliottdup.gettalents.viewmodel;

import android.app.Application;

import com.eliottdup.gettalents.data.repository.ReviewRepository;
import com.eliottdup.gettalents.data.repository.UserRepository;
import com.eliottdup.gettalents.model.Review;
import com.eliottdup.gettalents.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    private MutableLiveData<User> user;
    public MutableLiveData<List<Review>> reviewList = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);

        userRepository = new UserRepository();
        reviewRepository = new ReviewRepository();
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }
    
    public LiveData<User> getUser() {
        if (this.user == null) this.user = new MutableLiveData<>();
        return this.user;
    }
  
    public void getUserById(String id) {
        this.user = userRepository.getUserById(id);
    }

    public void getLoggedUser(String id) {
        this.user = userRepository.getUserById(id);
    }

    public void updateUser(String id, User user) {
        userRepository.updateUser(id, user);
    }

    public void getReceivedReviewsForUser(int userId) {
        reviewList = reviewRepository.getReceivedReviewsForUser(userId);
    }
}
