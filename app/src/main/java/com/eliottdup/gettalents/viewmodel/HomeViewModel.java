package com.eliottdup.gettalents.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eliottdup.gettalents.data.repository.CategoryRepository;
import com.eliottdup.gettalents.data.repository.UserRepository;
import com.eliottdup.gettalents.model.Category;
import com.eliottdup.gettalents.model.User;

import java.util.List;

import lombok.NonNull;

/**
 * Created by temp on 11/07/2021
 */
public class HomeViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public MutableLiveData<List<User>> users;
    public MutableLiveData<User> user;
    public MutableLiveData<List<Category>> categories;
    public MutableLiveData<Category> category;


    public HomeViewModel(@NonNull Application application) {
        super(application);

        categoryRepository = new CategoryRepository();
        userRepository = new UserRepository();
    }

    // users

    public void setUser(User user) { this.user.setValue(user); }

    public LiveData<User> getUser() {
        if(this.user == null) this.user = new MutableLiveData<>();
        return this.user;
    }

    public void getUserById(String id) { this.user = userRepository.getUserById(id); }

    public void getUsers() {
//        if(this.users == null) this.users = new MutableLiveData<>();
//        return this.users;
        this.users = userRepository.getAllUsers();
    }

    // categories

    public void setCategory(Category category) { this.category.setValue(category); }

    public LiveData<Category> getCategory() {
        if(this.category == null) this.category = new MutableLiveData<>();
        return this.category;
    }

    public void getCategporyById(String id) { this.category = categoryRepository.getCategoryById(id); }

    public void getCategories() {
//        if(this.categories == null) this.categories = new MutableLiveData<>();
//        return this.categories;
        this.categories = categoryRepository.getCategories();
    }



}
