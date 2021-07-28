package com.eliottdup.gettalents.ui.review;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.eliottdup.gettalents.R;

public class ReviewActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private ReviewFragment reviewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        fragmentManager = getSupportFragmentManager();

        configureDefaultFragment();
    }

    private void configureDefaultFragment() {
        if (reviewFragment == null) reviewFragment = ReviewFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, reviewFragment)
                .commit();
    }
}