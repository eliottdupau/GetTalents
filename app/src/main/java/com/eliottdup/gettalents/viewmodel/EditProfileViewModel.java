package com.eliottdup.gettalents.viewmodel;

import android.app.Application;

import com.eliottdup.gettalents.data.repository.UserRepository;
import com.eliottdup.gettalents.model.User;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class EditProfileViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public MutableLiveData<User> user;

    public EditProfileViewModel(@NonNull Application application) {
        super(application);

        userRepository = new UserRepository();
    }

    public void getLoggedUser() {
        // Todo() : Changer avec la méthode de réccupération du user logged
        this.user = userRepository.getUserById(1);
    }

    public void updateUser(int id, User user) {
        userRepository.updateUser(id, user);
    }
}
