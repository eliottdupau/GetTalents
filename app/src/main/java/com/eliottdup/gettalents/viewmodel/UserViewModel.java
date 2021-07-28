package com.eliottdup.gettalents.viewmodel;

import android.app.Application;

import com.eliottdup.gettalents.data.UserRepository;
import com.eliottdup.gettalents.model.User;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    private MutableLiveData<User> user;

    public UserViewModel(@NonNull Application application) {
        super(application);

        userRepository = new UserRepository();
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

    public void getLoggedUser() {
        this.user = userRepository.getLoggedUser();
    }

    public void updateUser(String id, User user) {
        this.user = userRepository.updateUser(id, user);
    }
}
