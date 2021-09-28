package com.eliottdup.gettalents.ui.map;

import android.app.Application;

import com.eliottdup.gettalents.data.repository.UserRepository;
import com.eliottdup.gettalents.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MapViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    public MapViewModel(@NonNull Application application) {
        super(application);

        userRepository = new UserRepository();
    }

    public MutableLiveData<List<User>> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
