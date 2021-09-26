package com.eliottdup.gettalents.data.repository;

import android.content.Intent;
import android.util.Log;

import com.eliottdup.gettalents.data.RetrofitInstance;
import com.eliottdup.gettalents.data.service.UserService;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.Picture;
import com.eliottdup.gettalents.model.Review;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private FirebaseAuth firebaseAuth;

    public UserRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public MutableLiveData<User> createUserInFirebase(String email, String password) {
        MutableLiveData<User> userInFirebase = new MutableLiveData<>();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                User user = new User();
                if (firebaseUser != null) {
                    user.setFirebaseUid(firebaseUser.getUid());
                    user.setEmail(firebaseUser.getEmail());
                }

                userInFirebase.setValue(user);
            } else {
                Log.d("Sign Up", "Failed");

                userInFirebase.postValue(null);
            }
        });

        return userInFirebase;
    }

    public MutableLiveData<User> signInWithFirebase(String email, String password) {
        MutableLiveData<User> userInFirebase = new MutableLiveData<>();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                User user = new User();
                if (firebaseUser != null) {
                    user.setFirebaseUid(firebaseUser.getUid());
                    user.setEmail(firebaseUser.getEmail());
                }

                userInFirebase.setValue(user);
            } else {
                userInFirebase.postValue(null);
            }
        });

        return userInFirebase;
    }

    public MutableLiveData<User> createUser(User user) {
        MutableLiveData<User> userInDB = new MutableLiveData<>();

        UserService userService = RetrofitInstance.getInstance().create(UserService.class);
        Call<Void> call = userService.createUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("HTTP 201", "User Creation -> success");

                userInDB.setValue(user);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("HTTP", "User Creation -> fail");

                userInDB.postValue(null);
            }
        });

        return userInDB;
    }

    public MutableLiveData<User> getUserById(String id) {
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

        UserService userService = RetrofitInstance.getInstance().create(UserService.class);
        Call<User> call = userService.getUserById(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                Log.d("HTTP 200", "Get User by Id -> success");
                userMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "User Not Found");
            }
        });

        return userMutableLiveData;
    }

    public MutableLiveData<List<User>> getAllUsers() {
        MutableLiveData<List<User>> usersMutableLiveData = new MutableLiveData<>();

        UserService userService = RetrofitInstance.getInstance().create(UserService.class);
        Call<List<User>> call = userService.getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                Log.d("HTTP 200", "Get User by Id -> success");
                usersMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "User Not Found");
            }
        });

        return usersMutableLiveData;
    }

    public void updateUser(String id, User user) {
        UserService userService = RetrofitInstance.getInstance().create(UserService.class);
        Call<Void> call = userService.updateUser(id, user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("HTTP 204", "Update User -> success");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "User Not Found");
            }
        });
    }

    public void deleteUser(String id) {
        UserService userService = RetrofitInstance.getInstance().create(UserService.class);
        Call<Void> call = userService.deleteUser(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("HTTP 200", "Delete User -> success");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "User Not Found");
            }
        });
    }

    public MutableLiveData<List<User>> getUsersByCategoryId(String id) {
        MutableLiveData<List<User>> usersMutableLiveData = new MutableLiveData<>();

        com.eliottdup.gettalents.data.service.UserService userService = RetrofitInstance.getInstance().create(com.eliottdup.gettalents.data.service.UserService.class);
        Call<List<User>> call = userService.getUsersByCategoryId(id);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@lombok.NonNull Call<List<User>> call, @lombok.NonNull Response<List<User>> response) {
                Log.d("HTTP 200", "Get Users By Category -> success");
                usersMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@lombok.NonNull Call<List<User>> call, @lombok.NonNull Throwable t) {
                Log.d("HTTP 404", "User Not Found");
            }
        });

        return usersMutableLiveData;
    }

    public MutableLiveData<List<User>> getUsersBySkillId(String id) {
        MutableLiveData<List<User>> usersMutableLiveData = new MutableLiveData<>();

        com.eliottdup.gettalents.data.service.UserService userService = RetrofitInstance.getInstance().create(com.eliottdup.gettalents.data.service.UserService.class);
        Call<List<User>> call = userService.getUsersBySkillId(id);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@lombok.NonNull Call<List<User>> call, @lombok.NonNull Response<List<User>> response) {
                Log.d("HTTP 200", "Get Users By Category -> success");
                usersMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@lombok.NonNull Call<List<User>> call, @lombok.NonNull Throwable t) {
                Log.d("HTTP 404", "User Not Found");
            }
        });

        return usersMutableLiveData;
    }


}
