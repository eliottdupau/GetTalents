package com.eliottdup.gettalents.viewmodel;

import android.app.Application;

import com.eliottdup.gettalents.data.repository.UserRepository;
import com.eliottdup.gettalents.model.User;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EditProfileViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    private MutableLiveData<User> user = new MutableLiveData<>();

    public EditProfileViewModel(@NonNull Application application) {
        super(application);

        userRepository = new UserRepository();
    }

    public LiveData<User> getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }

    public MutableLiveData<User> createUserInDB(User user) {
        return userRepository.createUser(user);
    }

    public void getLoggedUser(String id) {
        this.user = userRepository.getUserById(id);
    }

    public void updateUser(String id, User user) {
        userRepository.updateUser(id, user);
    }
}
