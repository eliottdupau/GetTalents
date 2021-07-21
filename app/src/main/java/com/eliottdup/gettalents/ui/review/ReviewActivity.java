package com.eliottdup.gettalents.ui.review;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.viewmodel.UserViewModel;

public class ReviewActivity extends AppCompatActivity {
    public static final String KEY_USER_ID = "userId";

    private UserViewModel viewModel;

    private FragmentManager fragmentManager;
    private ReviewFragment reviewFragment;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        userId = getIntent().getStringExtra(KEY_USER_ID);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        fragmentManager = getSupportFragmentManager();

        configureDefaultFragment();
    }

    private void configureDefaultFragment() {
        if (reviewFragment == null) reviewFragment = ReviewFragment.newInstance(userId);

        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, reviewFragment)
                .commit();
    }
}