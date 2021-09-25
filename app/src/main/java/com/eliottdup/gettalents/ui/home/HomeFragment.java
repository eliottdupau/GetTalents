package com.eliottdup.gettalents.ui.home;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Category;
import com.eliottdup.gettalents.model.Skill;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.ui.profile.consult.other.UserProfileActivity;
import com.eliottdup.gettalents.utils.ItemClickSupport;
import com.eliottdup.gettalents.viewmodel.HomeViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements CategoryAdapter.ICategorySelect {

    private TextInputLayout keyWordsLayout;
    private TextInputEditText keyWordsView;

    private RecyclerView categoryRecyclerView ;
    private RecyclerView homeUserRecyclerView ;

    private HomeViewModel homeViewModel;

    private CategoryAdapter categoryAdapter;
    private HomeUserAdapter homeUserAdapter;

    private List<Category> categories;
    private List<Skill> skills;
    private List<User> users;

    private List<User> usersByCategory;
    private List<User> usersBySkill;

    private List<String> selectedCategoriesNames;

    public static final String KEY_USER_ID = "userId";

    private FragmentManager fragmentManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        keyWordsLayout = root.findViewById(R.id.inputLayout_key_words);
        keyWordsView = root.findViewById(R.id.editText_key_words);
        categoryRecyclerView = root.findViewById(R.id.recyclerView_category);
        homeUserRecyclerView = root.findViewById(R.id.recyclerView_homeUser);
        keyWordsView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String keyWords = keyWordsView.getText().toString();
                onKeyWordInput(keyWords);
                return true;
            }
        });
        ItemClickSupport.addTo(homeUserRecyclerView, R.layout.item_home_user)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    User user = homeUserAdapter.getUser(position);
                    Intent intent = new Intent(getContext(), UserProfileActivity.class);
                    intent.putExtra(KEY_USER_ID, user.getId());
                    startActivity(intent);
                });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        configureCategoryRecyclerView();
        configureHomeUserRecyclerView();

        getCategories();
        getSkills();
        getUsers();

        this.selectedCategoriesNames = new ArrayList<>();

    }

    private void configureCategoryRecyclerView() {
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categories, Glide.with(this), this);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void configureHomeUserRecyclerView() {
        users = new ArrayList<>();
        homeUserAdapter = new HomeUserAdapter(users, Glide.with(this));
        homeUserRecyclerView.setAdapter(homeUserAdapter);
        homeUserRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    private void getCategories() {
        homeViewModel.getCategories();
        homeViewModel.categories.observe(getViewLifecycleOwner(), categories -> {
            this.categories = categories;
            categoryAdapter.updateData(this.categories);
        });
    }

    private void getSkills() {
        homeViewModel.getSkills();
        homeViewModel.skills.observe(getViewLifecycleOwner(), skills -> {
            this.skills = skills;
        });
    }

    private void getUsers() {
        homeViewModel.getUsers();
        homeViewModel.users.observe(getViewLifecycleOwner(), users -> {
            this.users = users;
            this.usersByCategory = users;
            this.usersBySkill = users;
            homeUserAdapter.updateData(this.users);
        });
    }

    public void updateUsersByFilters() {
        List<User> filteredUsers = new ArrayList<>();
        for (User userSkill : this.usersBySkill) {
            if(this.usersByCategory.contains(userSkill)) {
                filteredUsers.add(userSkill);
            }
        }
        homeUserAdapter.updateData(filteredUsers);
    }

    public void filterByCategory() {
        if(this.selectedCategoriesNames.isEmpty()) {
            this.usersByCategory = this.users;
        } else {
            List<User> tempUsers = new ArrayList<>();
            for (User user : this.users) {
                for (Skill skill : user.getSkills()) {
                    if(this.selectedCategoriesNames.contains(skill.getCategory().getName())) {
                        tempUsers.add(user);
                    }
                }
            }
            this.usersByCategory = tempUsers;
        }
        updateUsersByFilters();
    }

    public void filterBySkills(List<String> keyWords) {
        if(keyWords.isEmpty()) {
            this.usersBySkill = this.users;
        } else {
            List<User> tempUsers = new ArrayList<>();
            for (User user : this.users) {
                for (Skill userSkill : user.getSkills()) {
                    String userSkillName = userSkill.getName();
                    for (String keyWord: keyWords) {
                        String pattern = keyWord.toUpperCase().concat("[A-Z]*");
                        if(Pattern.matches(pattern, userSkillName.toUpperCase())) {
                            if(!tempUsers.contains(user)) {
                                tempUsers.add(user);
                            }
                        }
                    }
                }
            }
            this.usersBySkill = tempUsers;
        }
        updateUsersByFilters();
    }

    @Override
    public void onCategorySelect(String newCategoryName) {
        if(this.selectedCategoriesNames.contains(newCategoryName)) {
            List<String> tempCategoriesNamesList = new ArrayList<>();
            for (String categoryName: selectedCategoriesNames) {
                if(!categoryName.equals(newCategoryName)) {
                    tempCategoriesNamesList.add(categoryName);
                }
            }
            this.selectedCategoriesNames = tempCategoriesNamesList;
        } else {
            this.selectedCategoriesNames.add(newCategoryName);
        }
        filterByCategory();
    }

    public void onKeyWordInput(String keyWords) {
        List<String> keyWordsList = new ArrayList();
        if(keyWords.length()>3) {
            keyWordsList = Arrays.asList(keyWords.split(" "));
        }
        filterBySkills(keyWordsList);
    }

}