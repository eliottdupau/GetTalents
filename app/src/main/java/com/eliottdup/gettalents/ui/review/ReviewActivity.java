package com.eliottdup.gettalents.ui.review;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.eliottdup.gettalents.R;

public class ReviewActivity extends AppCompatActivity {
    public static final String KEY_USER_ID = "userId";

    private ReviewFragment reviewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        int userId = getIntent().getIntExtra(KEY_USER_ID, -1);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (reviewFragment == null) reviewFragment = ReviewFragment.newInstance(userId);

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, reviewFragment)
                .commit();
    }
}