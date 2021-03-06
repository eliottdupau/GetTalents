package com.eliottdup.gettalents.viewmodel;

import android.app.Application;

import com.eliottdup.gettalents.data.repository.UserRepository;
import com.eliottdup.gettalents.model.User;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MyProfileViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public MutableLiveData<User> user;

    public MyProfileViewModel(@NonNull Application application) {
        super(application);

        userRepository = new UserRepository();
    }

    public void getLoggedUser(String id) {
        this.user = userRepository.getUserById(id);
    }
}
