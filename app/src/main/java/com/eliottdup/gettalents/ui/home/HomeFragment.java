package com.eliottdup.gettalents.ui.home;

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
import android.widget.EditText;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Category;
import com.eliottdup.gettalents.model.Skill;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.ui.profile.consult.other.UserProfileActivity;
import com.eliottdup.gettalents.utils.DistanceUtils;
import com.eliottdup.gettalents.utils.ItemClickSupport;
import com.eliottdup.gettalents.viewmodel.HomeViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment implements CategoryAdapter.ICategorySelect {
    private EditText keyWordsView;

    private RecyclerView categoryRecyclerView ;
    private RecyclerView homeUserRecyclerView ;

    private HomeViewModel homeViewModel;

    private CategoryAdapter categoryAdapter;
    private HomeUserAdapter homeUserAdapter;

    private List<Category> categories;

    private FirebaseUser firebaseUser;
    private User loggedUser;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // link the layouts with the fragment
        keyWordsView = root.findViewById(R.id.editText_key_words);
        categoryRecyclerView = root.findViewById(R.id.recyclerView_category);
        homeUserRecyclerView = root.findViewById(R.id.recyclerView_homeUser);

        // event listener on key words input -> refresh the users display
        keyWordsView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String keyWords = keyWordsView.getText().toString();
                onKeyWordInput(keyWords);
                return true;
            }
        });

        // event on user card click -> redirection to the user profile
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

        // init view model
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        // get the logged user data from firebase
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // get the logged user data from the database
            // -> then configure the recycler views
            // -> then get the users from the database
        getLoggedUser();

        // init the filters data
        this.selectedCategoriesNames = new ArrayList<>();
        this.keyWordsList = new ArrayList<>();
    }

    private void configureCategoryRecyclerView() {
        // init the adapter with the categories list
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categories, this);

        // configure the recycler view with the adapter
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        getCategories();
    }

    private void configureHomeUserRecyclerView() {
        // init the adapter with the categories list
        users = new ArrayList<>();
        homeUserAdapter = new HomeUserAdapter(getContext(), users, this.loggedUser);

        // configure the recycler view with the adapter
        homeUserRecyclerView.setAdapter(homeUserAdapter);
        homeUserRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        getUsers();
    }

    // sort the users from the closer to the further
    private List<User> sortUsersByLocation(List<User> allUsers) {

        // check if the logged user had an address -> if not keep the order from database
        if (this.loggedUser.getAddresses().size() > 0) {

            // check if the logged user address has coordinates -> if not keep the order from database
            if (this.loggedUser.getAddresses().get(0).getLat() != 0 && this.loggedUser.getAddresses().get(0).getLng() != 0 ) {

                // get the coordinates of the first address of the logged user
                double latUser1 = this.loggedUser.getAddresses().get(0).getLat();
                double lngUser1 = this.loggedUser.getAddresses().get(0).getLng();

                // make a list that associates each user and its distance from the current user address
                List<Pair<Double, User>> distUserCouplesList = new ArrayList<>();
                for (User user: allUsers) {
                    if (user.getAddresses().size() > 0) {
                        // get the coordinates of the first address of the user of the list
                        double latUser2 = user.getAddresses().get(0).getLat();
                        double lngUser2 = user.getAddresses().get(0).getLng();
                        // get the distance in kms with the coordinates of each users
                        double distance = DistanceUtils.calculateDistance(latUser1, latUser2, lngUser1, lngUser2);
                        // make a pair distance - user and add it to the list
                        Pair<Double, User> distUserCouple = new Pair<Double, User>(distance, user);
                        distUserCouplesList.add(distUserCouple);
                    }
                }

                // sort the list that associate user and distance from the closer to the further
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

                // make a new list with only the users users and send it back
                List<User> newUsersList = new ArrayList<>();
                for(Pair<Double, User> distUserCouple: distUserCouplesList) {
                    newUsersList.add(distUserCouple.second);
                }
                return newUsersList;

            } else {

                // if there are no means to determinate the distance between the logged user and the others return the list as it is
                return allUsers;

            }

        } else {

            // if there are no means to determinate the distance between the logged user and the others return the list as it is
            return allUsers;

        }

    }

    // get the logged user data
    private void getLoggedUser() {
        if (this.firebaseUser != null) {
            homeViewModel.getLoggedUser(this.firebaseUser.getUid());
            homeViewModel.user.observe(getViewLifecycleOwner(), loggedUser -> {
                this.loggedUser = loggedUser;
                // launch the configuration of the recycler view
                configureCategoryRecyclerView();
                configureHomeUserRecyclerView();
            });
        }
    }

    // get the categories data
    private void getCategories() {
        homeViewModel.getCategories();
        homeViewModel.categories.observe(getViewLifecycleOwner(), categories -> {
            this.categories = categories;
            categoryAdapter.updateData(this.categories);
        });
    }

    // get the users data
    private void getUsers() {
        homeViewModel.getUsers();
        homeViewModel.users.observe(getViewLifecycleOwner(), users -> {
            // remove the logged user from the list
            List<User> otherUsers = new ArrayList<>();
            for (User user: users) {
                if(! user.getFirebaseUid().equals(firebaseUser.getUid())) {
                    otherUsers.add(user);
                }
            }
            // update the user list and the filtered user list
            this.users = sortUsersByLocation(otherUsers);
            this.filteredUsers = sortUsersByLocation(otherUsers);
            homeUserAdapter.updateData(sortUsersByLocation(otherUsers));
        });
    }

    // add a user in the filtered users list after checking that he is not in
    public void addUserToFilteredList(User user) {
        if(!this.filteredUsers.contains(user)) {
            this.filteredUsers.add(user);
        }
    }

    // fill the filtered users list and update the display
    public void filterUsers() {

        // if the logged user doesn't use any filters
        if (this.keyWordsList.isEmpty() && this.selectedCategoriesNames.isEmpty()) {
            this.filteredUsers = this.users;

        // if the logged user uses filters
        } else {

            this.filteredUsers = new ArrayList<>();
            for (User user : this.users) {

                // browse the user skills list - if void the user won't be recorded
                for (Skill userSkill : user.getSkills()) {

                    // if the logged user filters by skills
                    if (!this.keyWordsList.isEmpty()) {

                        // check all the skills that fits each keyword
                        for (String keyWord: this.keyWordsList) {
                            String userSkillName = userSkill.getName();
                            String pattern = keyWord.toUpperCase().concat("[A-Z]*");
                            if(Pattern.matches(pattern, userSkillName.toUpperCase())) {
                                // if the logged user only filters by skills
                                if (!this.selectedCategoriesNames.isEmpty()) {
                                    String userCategoryName = userSkill.getCategory().getName();
                                    if(this.selectedCategoriesNames.contains(userCategoryName)) {
                                        addUserToFilteredList(user);
                                    }
                                // if the logged user filters by categories and skills
                                } else {
                                    addUserToFilteredList(user);
                                }
                            }
                        }

                    // if the logged user only filters by categories
                    } else if (!this.selectedCategoriesNames.isEmpty()) {
                        String userCategoryName = userSkill.getCategory().getName();
                        if(this.selectedCategoriesNames.contains(userCategoryName)) {
                            addUserToFilteredList(user);
                        }
                    }
                }
            }
        }
        // update display
        homeUserAdapter.updateData(this.filteredUsers);
    }

    // launch when the user clicks on the category name
    @Override
    public void onCategorySelect(String newCategoryName) {
        // check if the category name is in the select categories list
        if (this.selectedCategoriesNames.contains(newCategoryName)) {
            // if so remove it from the list
            List<String> tempCategoriesNamesList = new ArrayList<>();
            for (String categoryName: selectedCategoriesNames) {
                if(!categoryName.equals(newCategoryName)) {
                    tempCategoriesNamesList.add(categoryName);
                }
            }
            this.selectedCategoriesNames = tempCategoriesNamesList;
        } else {
            // if it's not the case add it to the list
            this.selectedCategoriesNames.add(newCategoryName);
        }
        // launch the update the filtered users list
        filterUsers();
    }

    // launch when the user fills the key words input
    public void onKeyWordInput(String keyWords) {
        // reset the key words list and only fill it the the input contains at least 3 characters
        this.keyWordsList = new ArrayList<>();
        if(keyWords.length()>2) {
            this.keyWordsList = Arrays.asList(keyWords.split(" "));
        }
        // launch the update the filtered users list
        filterUsers();
    }

}