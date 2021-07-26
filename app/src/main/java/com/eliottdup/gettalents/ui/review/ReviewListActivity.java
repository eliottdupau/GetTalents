package com.eliottdup.gettalents.ui.review;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.viewmodel.UserViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class ReviewListActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;

    private UserViewModel viewModel;

    private FragmentManager fragmentManager;
    private ReviewListFragment reviewListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        toolbar = findViewById(R.id.topAppBar);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        fragmentManager = getSupportFragmentManager();

        configureToolbar();
        setupView();
        getUser();
    }

    private void configureToolbar() {
        toolbar.setTitle(getString(R.string.title_my_reviews));
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void setupView() {
        if (reviewListFragment == null) reviewListFragment = ReviewListFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, reviewListFragment)
                .commit();
    }

    private void getUser() {
        viewModel.getLoggedUser();
    }
}