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
import com.eliottdup.gettalents.ui.home.CategoryAdapter;
import com.eliottdup.gettalents.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private TextInputLayout keyWordsLayout;
    private TextInputEditText keyWordsView;
    private RecyclerView recyclerView;

    private CategoryAdapter adapter;

    private List<Category> categories;

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
        recyclerView = root.findViewById(R.id.recyclerView_category);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        configureRecyclerView();

        setupView();
    }

    private void configureRecyclerView() {
        categories = new ArrayList<>();
        adapter = new CategoryAdapter(categories, Glide.with(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
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

        adapter.updateData(categories);

    }


}