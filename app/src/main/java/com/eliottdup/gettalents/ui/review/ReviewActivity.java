package com.eliottdup.gettalents.ui.review;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.viewmodel.UserViewModel;

public class ReviewActivity extends AppCompatActivity {
    private UserViewModel viewModel;

    private FragmentManager fragmentManager;
    private ReviewFragment reviewFragment;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        fragmentManager = getSupportFragmentManager();

        getUser();
        configureDefaultFragment();
    }

    private void getUser() {
        user = viewModel.getUser().getValue();
    }

    private void configureDefaultFragment() {
        if (reviewFragment == null) reviewFragment = ReviewFragment.newInstance(user.getId());

        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, reviewFragment)
                .commit();
    }
}