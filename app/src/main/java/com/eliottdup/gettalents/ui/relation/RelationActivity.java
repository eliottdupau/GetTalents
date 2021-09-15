package com.eliottdup.gettalents.ui.relation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.viewmodel.UserViewModel;
import com.google.android.material.appbar.MaterialToolbar;

// Todo() : Configurer la recherche par nom
public class RelationActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;

    private UserViewModel viewModel;

    private FragmentManager fragmentManager;
    private RelationFragment relationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);

        toolbar = findViewById(R.id.topAppBar);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        fragmentManager = getSupportFragmentManager();

        configureToolbar();
        setupView();
        getUser();
    }

    private void configureToolbar() {
        toolbar.setTitle(getString(R.string.title_my_relations));
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void setupView() {
        if (relationFragment == null) relationFragment = RelationFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, relationFragment)
                .commit();
    }

    private void getUser() {
        viewModel.getLoggedUser();
    }
}