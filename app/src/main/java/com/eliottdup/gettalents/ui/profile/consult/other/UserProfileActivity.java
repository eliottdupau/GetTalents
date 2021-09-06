package com.eliottdup.gettalents.ui.profile.consult.other;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.review.ReviewListFragment;
import com.eliottdup.gettalents.viewmodel.UserProfileViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class UserProfileActivity extends AppCompatActivity {
    public static final String KEY_USER_ID = "userId";

    private MaterialToolbar toolbar;

    private UserProfileViewModel viewModel;

    private UserProfileFragment userProfileFragment;
    private ReviewListFragment reviewListFragment;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        toolbar = findViewById(R.id.topAppBar);

        userId = getIntent().getIntExtra(KEY_USER_ID, 0);

        viewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);

        FragmentManager fragmentManager = getSupportFragmentManager();

        configureToolbar();
        getData();

        if (userProfileFragment == null) userProfileFragment = UserProfileFragment.newInstance();
        if (reviewListFragment == null) reviewListFragment = ReviewListFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, userProfileFragment)
                .replace(R.id.reviewsFragmentContainer, reviewListFragment)
                .commit();
    }

    private void configureToolbar() {
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void getData() {
        viewModel.getUserById(userId);
        viewModel.user.observe(this, user -> toolbar.setTitle(user.getPseudo()));
    }
}