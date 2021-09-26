package com.eliottdup.gettalents.ui.profile.consult.other;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.review.ReviewListFragment;
import com.eliottdup.gettalents.utils.KeyUtils;
import com.eliottdup.gettalents.viewmodel.UserProfileViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;

    private UserProfileViewModel viewModel;

    private UserProfileFragment userProfileFragment;
    private ReviewListFragment reviewListFragment;

    //private int userId;
    private String userFirebaseUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        toolbar = findViewById(R.id.topAppBar);

        //userId = getIntent().getIntExtra(KeyUtils.KEY_USER_ID, 0);
        userFirebaseUid = getIntent().getStringExtra(KeyUtils.KEY_FIREBASE_UID);

        viewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);

        FragmentManager fragmentManager = getSupportFragmentManager();

        configureToolbar();
        getData();

        if (userProfileFragment == null) userProfileFragment = UserProfileFragment.newInstance();
        if (reviewListFragment == null) reviewListFragment = ReviewListFragment.newInstance(userFirebaseUid);

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, userProfileFragment)
                .replace(R.id.reviewsFragmentContainer, reviewListFragment)
                .commit();
    }

    private void configureToolbar() {
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void getData() {
        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            viewModel.getUserById();
            viewModel.user.observe(this, u -> toolbar.setTitle(u.getPseudo()));
        }*/

        viewModel.getUserById(userFirebaseUid);
        viewModel.user.observe(this, u -> toolbar.setTitle(u.getPseudo()));
    }
}