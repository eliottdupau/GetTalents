package com.eliottdup.gettalents.viewmodel;

import android.app.Application;

import com.eliottdup.gettalents.data.repository.UserRepository;
import com.eliottdup.gettalents.model.User;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class AuthenticationViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    public MutableLiveData<User> userInFirebase;
    public MutableLiveData<User> userInDB;

    public AuthenticationViewModel(@NonNull Application application) {
        super(application);

        userRepository = new UserRepository();
    }

    public void createUserInFirebase(String email, String password) {
        userInFirebase = userRepository.createUserInFirebase(email, password);
    }

    public void createUserInDB(User user) {
        userInDB = userRepository.createUser(user);
    }

    public void getUserInFirebase(String email, String password) {
        userInFirebase = userRepository.getUserInFirebase(email, password);
    }
}
