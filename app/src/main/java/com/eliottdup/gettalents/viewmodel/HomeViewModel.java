package com.eliottdup.gettalents.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eliottdup.gettalents.data.repository.CategoryRepository;
import com.eliottdup.gettalents.data.repository.SkillRepository;
import com.eliottdup.gettalents.data.repository.UserRepository;
import com.eliottdup.gettalents.model.Category;
import com.eliottdup.gettalents.model.Skill;
import com.eliottdup.gettalents.model.User;

import java.util.List;

import lombok.NonNull;

/**
 * Created by temp on 11/07/2021
 */
public class HomeViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SkillRepository skillRepository;

    public MutableLiveData<List<User>> users;
    public MutableLiveData<User> user;
    public MutableLiveData<List<Category>> categories;
    public MutableLiveData<Category> category;
    public MutableLiveData<List<Skill>> skills;
    public MutableLiveData<Skill> skill;


    public HomeViewModel(@NonNull Application application) {
        super(application);

        categoryRepository = new CategoryRepository();
        userRepository = new UserRepository();
        skillRepository = new SkillRepository();
    }

    public void getLoggedUser(String id) {
        this.user = userRepository.getUserById(id);
    }

    // users

    public void setUser(User user) { this.user.setValue(user); }

    public LiveData<User> getUser() {
        if(this.user == null) this.user = new MutableLiveData<>();
        return this.user;
    }

    public void getUsers() {
        this.users = userRepository.getAllUsers();
    }

    public void getUsersByCategoryId(String id) {
        this.users = userRepository.getUsersByCategoryId(id);
    }

    public void getUsersBySkillId(String id) {
        this.users = userRepository.getUsersBySkillId(id);
    }

    // categories

    public void getCategories() {
        this.categories = categoryRepository.getCategories();
    }

    // skills

    public void getSkills() {
        this.skills = skillRepository.getSkills();
    }

}
