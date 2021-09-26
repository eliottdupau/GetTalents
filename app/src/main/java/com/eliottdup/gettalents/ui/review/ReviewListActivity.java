package com.eliottdup.gettalents.ui.review;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ReviewListActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;

    private ReviewListFragment reviewListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        toolbar = findViewById(R.id.topAppBar);

        FragmentManager fragmentManager = getSupportFragmentManager();

        configureToolbar();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (reviewListFragment == null) reviewListFragment = ReviewListFragment.newInstance(user.getUid());

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, reviewListFragment)
                .commit();
    }

    private void configureToolbar() {
        toolbar.setTitle(getString(R.string.title_my_reviews));
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }
}