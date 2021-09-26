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

import android.util.Pair;
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
import com.eliottdup.gettalents.utils.DistanceUtils;
import com.eliottdup.gettalents.utils.ItemClickSupport;
import com.eliottdup.gettalents.viewmodel.HomeViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
    private List<User> users;

    private List<User> filteredUsers;

    private List<String> selectedCategoriesNames;
    private List<String> keyWordsList;

    public static final String KEY_USER_ID = "userId";

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
        getUsers();

        this.selectedCategoriesNames = new ArrayList<>();
        this.keyWordsList = new ArrayList<>();
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

    private List<User> sortUsersByLocation(List<User> allUsers) {

        double latUser1 = 50.883331;
        double lngUser1 = 1.66667;

        List<Pair<Double, User>> distUserCouplesList = new ArrayList<>();
        for (User user: allUsers) {
            double latUser2 = user.getAddresses().get(0).getLat();
            double lngUser2 = user.getAddresses().get(0).getLng();
            double distance = DistanceUtils.calculateDistance(latUser1, latUser2, lngUser1, lngUser2);
            Pair<Double, User> distUserCouple = new Pair<Double, User>(distance, user);
            distUserCouplesList.add(distUserCouple);
        }

        Collections.sort(distUserCouplesList, new Comparator<Pair<Double, User>>() {
            @Override
            public int compare(final Pair<Double, User> p1, final Pair<Double, User> p2) {
                if (p1.first < p2.first) {
                    return -1;
                } else if (p1.first.equals(p2.first)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        List<User> newUsersList = new ArrayList<>();
        for(Pair<Double, User> distUserCouple: distUserCouplesList) {
            newUsersList.add(distUserCouple.second);
        }

        return newUsersList;
    }

    private void getCategories() {
        homeViewModel.getCategories();
        homeViewModel.categories.observe(getViewLifecycleOwner(), categories -> {
            this.categories = categories;
            categoryAdapter.updateData(this.categories);
        });
    }

    private void getUsers() {
        homeViewModel.getUsers();
        homeViewModel.users.observe(getViewLifecycleOwner(), users -> {
            this.users = sortUsersByLocation(users);
            this.filteredUsers = sortUsersByLocation(users);
            homeUserAdapter.updateData(this.users);
        });
    }

    public void addUserToFilteredList(User user) {
        if(!this.filteredUsers.contains(user)) {
            this.filteredUsers.add(user);
        }
    }

    public void filterUsers() {
        this.filteredUsers = new ArrayList<>();
        for (User user : this.users) {
            for (Skill userSkill : user.getSkills()) {
                if (!this.keyWordsList.isEmpty()) {
                    for (String keyWord: this.keyWordsList) {
                        String userSkillName = userSkill.getName();
                        String pattern = keyWord.toUpperCase().concat("[A-Z]*");
                        if(Pattern.matches(pattern, userSkillName.toUpperCase())) {
                            if (!this.selectedCategoriesNames.isEmpty()) {
                                String userCategoryName = userSkill.getCategory().getName();
                                if(this.selectedCategoriesNames.contains(userCategoryName)) {
                                    addUserToFilteredList(user);
                                }
                            } else {
                                addUserToFilteredList(user);
                            }
                        }
                    }
                } else if (!this.selectedCategoriesNames.isEmpty()) {
                    String userCategoryName = userSkill.getCategory().getName();
                    if(this.selectedCategoriesNames.contains(userCategoryName)) {
                        addUserToFilteredList(user);
                    }
                } else {
                    this.filteredUsers = this.users;
                }
            }
        }
        homeUserAdapter.updateData(this.filteredUsers);
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
        filterUsers();
    }

    public void onKeyWordInput(String keyWords) {
        this.keyWordsList = new ArrayList<>();
        if(keyWords.length()>3) {
            this.keyWordsList = Arrays.asList(keyWords.split(" "));
        }
        filterUsers();
    }

}