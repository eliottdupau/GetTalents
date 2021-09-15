package com.eliottdup.gettalents.ui.home;

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
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.Category;
import com.eliottdup.gettalents.model.Picture;
import com.eliottdup.gettalents.model.Skill;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.viewmodel.HomeViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
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

    private Category category;
    private List<Category> categories;
    private List<User> users;

    private String selectedCategoryName;

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
                String keyWord = keyWordsView.getText().toString();
                onKeyWordInput(keyWord);
                return true;
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        configureCategoryRecyclerView();
        configureHomeUserRecyclerView();

        selectedCategoryName = "";

//        getCategories();
//        getUsers();

        // temp
        setupViewTemp();
    }

    private void configureCategoryRecyclerView() {
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categories, Glide.with(this), selectedCategoryName, this);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onCategorySelect(String newCategoryName) {
        if(newCategoryName != selectedCategoryName) {
            selectedCategoryName = newCategoryName;
            getUsersByCategoryName(newCategoryName);
        } else {
            selectedCategoryName = "";
            // temp
            setupViewTemp();
        }
    }

    public void onKeyWordInput(String keyWord) {
        if(keyWord.length()>3) {
            getUsersBySkillName(keyWord);
        } else {
            // temp
            setupViewTemp();
        }
    }

    private void configureHomeUserRecyclerView() {
        users = new ArrayList<>();
        homeUserAdapter = new HomeUserAdapter(users, Glide.with(this));
        homeUserRecyclerView.setAdapter(homeUserAdapter);
//        homeUserRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        homeUserRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }



    private void getCategory() {
        homeViewModel.getCategory().observe(getViewLifecycleOwner(), category -> {
            this.category = category;
        });
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
            this.users = users;
            homeUserAdapter.updateData(this.users);
        });
    }

    private void getUsersByCategoryName(String categoryName) {

        // temp methode
        List<User> newUsers = new ArrayList<>();
        for (User user : users) {
            List<Skill> skills = user.getSkills();
            if (skills != null && !skills.isEmpty()) {
                for (Skill skill : skills) {
                    Category skillCategory = skill.getCategory();
                    if(skillCategory.getName() == categoryName) {
                        if(!newUsers.contains(user)) {
                            newUsers.add(user);
                        }
                    }
                }
            }
        }
        homeUserAdapter.updateData(newUsers);
    }

    private void getUsersBySkillName(String keyword) {

        // temp methode
        List<User> newUsers = new ArrayList<>();
        for (User user : users) {
            List<Skill> skills = user.getSkills();
            if (skills != null && !skills.isEmpty()) {
                for (Skill skill : skills) {
                    String skillName = skill.getName();
                    String pattern = keyword.toUpperCase().concat("[A-Z]*");
                    if(Pattern.matches(pattern, skillName.toUpperCase())) {
                        if(!newUsers.contains(user)) {
                            newUsers.add(user);
                        }
                    }
                }
            }
        }
        homeUserAdapter.updateData(newUsers);
    }


    private void setupViewTemp() {

        // temp skills

        Skill skill_1 = new Skill();
        skill_1.setIdskill(1);
        skill_1.setName("Menuiserie");

        Skill skill_2 = new Skill();
        skill_2.setIdskill(2);
        skill_2.setName("Plomberie");

        Skill skill_3 = new Skill();
        skill_3.setIdskill(3);
        skill_3.setName("Plantation");

        Skill skill_4 = new Skill();
        skill_4.setIdskill(4);
        skill_4.setName("Arbustes");

        Skill skill_5 = new Skill();
        skill_5.setIdskill(5);
        skill_5.setName("Toilettage");

        Skill skill_6 = new Skill();
        skill_6.setIdskill(6);
        skill_6.setName("Chien");

        // temp categories

        Category category_1 = new Category();
        category_1.setId(1);
        category_1.setName("Bricolage");
        Picture picture_cat_1 = new Picture();
        picture_cat_1.setId(1);
        // https://www.iconexperience.com/g_collection/search/?q=aniaml
        picture_cat_1.setPath("https://d1nhio0ox7pgb.cloudfront.net/_img/g_collection_png/standard/128x128/tools.png");
        category_1.setCategoryPicture(picture_cat_1);
        ArrayList<Skill> skills_cat_1 = new ArrayList<>();
        skills_cat_1.add(skill_1);
        skill_1.setCategory(category_1);
        skills_cat_1.add(skill_2);
        skill_2.setCategory(category_1);
        category_1.setSkills(skills_cat_1);

        Category category_2 = new Category();
        category_2.setId(2);
        category_2.setName("Jardinage");
        Picture picture_cat_2 = new Picture();
        picture_cat_2.setId(2);
        picture_cat_2.setPath("https://d1nhio0ox7pgb.cloudfront.net/_img/g_collection_png/standard/128x128/plant.png");
        category_2.setCategoryPicture(picture_cat_2);
        ArrayList<Skill> skills_cat_2 = new ArrayList<>();
        skills_cat_2.add(skill_3);
        skill_3.setCategory(category_2);
        skills_cat_2.add(skill_4);
        skill_4.setCategory(category_2);
        category_2.setSkills(skills_cat_2);

        Category category_3 = new Category();
        category_3.setId(3);
        category_3.setName("Animaux");
        Picture picture_cat_3 = new Picture();
        picture_cat_3.setId(3);
        picture_cat_3.setPath("https://d1nhio0ox7pgb.cloudfront.net/_img/g_collection_png/standard/128x128/dog.png");
        category_3.setCategoryPicture(picture_cat_3);
        ArrayList<Skill> skills_cat_3 = new ArrayList<>();
        skills_cat_3.add(skill_5);
        skill_5.setCategory(category_3);
        skills_cat_3.add(skill_6);
        skill_6.setCategory(category_3);
        category_3.setSkills(skills_cat_3);

        categories = new ArrayList<>();
        categories.add(category_1);
        categories.add(category_2);
        categories.add(category_3);

        categoryAdapter.updateData(categories);

        // temp users

        User user_1 = new User("1");
        user_1.setPseudo("Chilperic");
        user_1.setMail("chilperic@mail.com");
        user_1.setPhone(123456789);
        user_1.setPresentation("Je suis un bricoleur et un ami des animaux.");
        Picture picture_1 = new Picture();
        picture_1.setId(1);
        picture_1.setPath("https://img-4.linternaute.com/M-OdZ18KuQUin9akqJYOZxqk7MY=/620x415/smart/0f57298c13314fd6a9a6879327570405/ccmcms-linternaute/bebe-singe.jpg");
        user_1.setProfilePicture(picture_1);

        User user_2 = new User("2");
        user_2.setPseudo("Foulque");
        user_2.setMail("foulque@mail.com");
        user_2.setPhone(123456789);
        user_2.setPresentation("Je n'aime rien.");
        Picture picture_2 = new Picture();
        picture_2.setId(2);
        picture_2.setPath("https://img-4.linternaute.com/-62RZbasdwxBe5rTtu0u6aR9ekQ=/620x435/smart/fc15734ed3a741fbb59cf051cce0a81e/ccmcms-linternaute/bebe-fennec.jpg");
        user_2.setProfilePicture(picture_2);

        User user_3 = new User("3");
        user_3.setPseudo("Gonzague");
        user_3.setMail("gonzague@mail.com");
        user_3.setPhone(123456789);
        user_2.setPresentation("Je suis un bricoleur et un jardinier.");
        Picture picture_3 = new Picture();
        picture_3.setId(3);
        picture_3.setPath("https://torange.biz/photofxnew/76/HD/lion-profile-picture-76801.jpg");
        user_3.setProfilePicture(picture_3);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 13);
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.YEAR, 1992);
        user_1.setRegistrationDate(calendar.getTime());
        user_1.setBirthday(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, 5);
        calendar.set(Calendar.MONTH, 7);
        calendar.set(Calendar.YEAR, 1962);
        user_2.setRegistrationDate(calendar.getTime());
        user_2.setBirthday(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, 24);
        calendar.set(Calendar.MONTH, 10);
        calendar.set(Calendar.YEAR, 1978);
        user_3.setRegistrationDate(calendar.getTime());
        user_3.setBirthday(calendar.getTime());

        ArrayList<Address> addresses_1 = new ArrayList<>();

        Address address_1_1 = new Address(UUID.randomUUID().toString());
        address_1_1.setAddress("36 rue des jambons");
        address_1_1.setZipCode("69360");
        address_1_1.setCity("Youpi-en-Josas");
        address_1_1.setCountry("France");
        addresses_1.add(address_1_1);
        Address address_1_2 = new Address(UUID.randomUUID().toString());
        address_1_2.setAddress("12 rue du navet");
        address_1_2.setZipCode("39000");
        address_1_2.setCity("Ploin");
        address_1_2.setCountry("France");
        addresses_1.add(address_1_2);
        user_1.setAddresses(addresses_1);

        ArrayList<Address> addresses_2 = new ArrayList<>();
        Address address_2 = new Address(UUID.randomUUID().toString());
        address_2.setAddress("1 rue du chat anxieux");
        address_2.setZipCode("60000");
        address_2.setCity("Saint-Glinglin");
        address_2.setCountry("France");
        addresses_2.add(address_2);
        user_2.setAddresses(addresses_2);

        ArrayList<Address> addresses_3 = new ArrayList<>();
        Address address_3 = new Address(UUID.randomUUID().toString());
        address_3.setAddress("<23 rue des huîtres malades>");
        address_3.setZipCode("12100");
        address_3.setCity("Bouldogne");
        address_3.setCountry("France");
        addresses_3.add(address_3);
        user_3.setAddresses(addresses_3);

        ArrayList<Skill> skills_user_1 = new ArrayList<>();
        skills_user_1.add(skill_1);
        skills_user_1.add(skill_5);
        user_1.setSkills(skills_user_1);

        ArrayList<Skill> skills_user_2 = new ArrayList<>();
        user_2.setSkills(skills_user_2);

        ArrayList<Skill> skills_user_3 = new ArrayList<>();
        skills_user_3.add(skill_1);
        skills_user_3.add(skill_2);
        skills_user_3.add(skill_3);
        user_3.setSkills(skills_user_3);

        users = new ArrayList<>();
        users.add(user_1);
        users.add(user_2);
        users.add(user_3);

        homeUserAdapter.updateData(users);

    }


}