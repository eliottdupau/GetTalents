package com.eliottdup.gettalents.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.Category;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.ui.home.CategoryAdapter;
import com.eliottdup.gettalents.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private TextInputLayout keyWordsLayout;
    private TextInputEditText keyWordsView;

    private RecyclerView categoryRecyclerView ;
    private RecyclerView homeUserRecyclerView ;

    private CategoryAdapter categoryAdapter;
    private HomeUserAdapter homeUserAdaptater;

    private List<Category> categories;

    private List<User> users;

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
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        configureCategoryRecyclerView();
        configureHomeUserRecyclerView();

        setupView();
    }

    private void configureCategoryRecyclerView() {
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categories, Glide.with(this));
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void configureHomeUserRecyclerView() {
        users = new ArrayList<>();
        homeUserAdaptater = new HomeUserAdapter(users, Glide.with(this));
        homeUserRecyclerView.setAdapter(homeUserAdaptater);
        homeUserRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setupView() {

        Category category_1 = new Category("1");
        category_1.setName("BRICOLAGE");
        category_1.setIcon("https://www.paysagiste.info/wp-content/uploads/2017/04/se-lancer-en-jardinage-768x0-c-default.jpg");

        Category category_2 = new Category("2");
        category_2.setName("INFORMATIQUE");
        category_2.setIcon("https://www.paysagiste.info/wp-content/uploads/2017/04/se-lancer-en-jardinage-768x0-c-default.jpg");

        Category category_3 = new Category("3");
        category_3.setName("CUISINE");
        category_3.setIcon("https://www.paysagiste.info/wp-content/uploads/2017/04/se-lancer-en-jardinage-768x0-c-default.jpg");

        categories = new ArrayList<>();
        categories.add(category_1);
        categories.add(category_2);
        categories.add(category_3);

        categoryAdapter.updateData(categories);

        User user_1 = new User("1");
        user_1.setPseudo("Chilperic");
        user_1.setMail("chilperic@mail.com");
        user_1.setUrlProfilePicture("https://img-4.linternaute.com/M-OdZ18KuQUin9akqJYOZxqk7MY=/620x415/smart/0f57298c13314fd6a9a6879327570405/ccmcms-linternaute/bebe-singe.jpg");

        User user_2 = new User("2");
        user_2.setPseudo("Foulque");
        user_2.setMail("foulque@mail.com");
        user_2.setUrlProfilePicture("https://img-4.linternaute.com/-62RZbasdwxBe5rTtu0u6aR9ekQ=/620x435/smart/fc15734ed3a741fbb59cf051cce0a81e/ccmcms-linternaute/bebe-fennec.jpg");

        User user_3 = new User("3");
        user_3.setPseudo("Gonzague");
        user_3.setMail("gonzague@mail.com");
        user_3.setUrlProfilePicture("https://torange.biz/photofxnew/76/HD/lion-profile-picture-76801.jpg");

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 13);
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.YEAR, 1992);
        user_1.setBirthday(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, 5);
        calendar.set(Calendar.MONTH, 7);
        calendar.set(Calendar.YEAR, 1962);
        user_2.setBirthday(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, 24);
        calendar.set(Calendar.MONTH, 10);
        calendar.set(Calendar.YEAR, 1978);
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

        users = new ArrayList<>();
        users.add(user_1);
        users.add(user_2);
        users.add(user_3);

        homeUserAdaptater.updateData(users);

    }


}