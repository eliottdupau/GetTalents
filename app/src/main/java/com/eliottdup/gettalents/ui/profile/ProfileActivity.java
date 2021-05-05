package com.eliottdup.gettalents.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.profile.consult.ConsultProfileFragment;
import com.google.android.material.appbar.MaterialToolbar;

public class ProfileActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.topAppBar);

        fragmentManager = getSupportFragmentManager();

        configureToolbar();
        configureDefaultFragment();
    }

    private void configureToolbar() {
        toolbar.setTitle(getString(R.string.title_profile));

        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void configureDefaultFragment() {
        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, ConsultProfileFragment.newInstance())
                .commit();
    }
}