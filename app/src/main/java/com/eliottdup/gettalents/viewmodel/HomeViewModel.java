package com.eliottdup.gettalents.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eliottdup.gettalents.data.CategoryRepository;
import com.eliottdup.gettalents.data.SkillRepository;
import com.eliottdup.gettalents.data.UserRepository;
import com.eliottdup.gettalents.model.Category;
import com.eliottdup.gettalents.model.Skill;
import com.eliottdup.gettalents.model.User;

import java.util.ArrayList;
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

    // users

    public void setUser(User user) { this.user.setValue(user); }

    public LiveData<User> getUser() {
        if(this.user == null) this.user = new MutableLiveData<>();
        return this.user;
    }

    public void getUserById(String id) { this.user = userRepository.getUserById(id); }

    public void getUsers() {
        this.users = userRepository.getUsers();
    }

    public void getUsersByCategoryId(String id) {
        this.users = userRepository.getUsersByCategoryId(id);
    }

    public void getUsersBySkillId(String id) {
        this.users = userRepository.getUsersBySkillId(id);
    }

//    public void getUsersByCategoryAndSkillId(String categoryId, String skillId) {
//        MutableLiveData<List<User>> usersByCategory = userRepository.getUsersByCategoryId(categoryId);
//        MutableLiveData<List<User>> usersBySkill = userRepository.getUsersBySkillId(skillId);
//        List<User> tempList = new ArrayList<>();
//        for (User userBySkill : usersBySkill.getValue()) {
//            if(usersByCategory.getValue().contains(userBySkill)) {
//                tempList.add(userBySkill);
//            }
//        }
//        this.users.setValue(tempList);
//    }

    // categories

    public void setCategory(Category category) { this.category.setValue(category); } // inutilisé

    public LiveData<Category> getCategory() {
        if(this.category == null) this.category = new MutableLiveData<>();
        return this.category;
    } // inutilisé

    public void getCategporyById(String id) { this.category = categoryRepository.getCategoryById(id); } // inutilisé

    public void getCategories() {
        this.categories = categoryRepository.getCategories();
    }

    // skills

    public void getSkills() {
        this.skills = skillRepository.getSkills();
    }

}