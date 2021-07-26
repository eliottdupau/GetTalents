package com.eliottdup.gettalents.ui.profile.consult.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.profile.edit.EditProfileActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class MyProfileActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;

    private MyProfileFragment myProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        toolbar = findViewById(R.id.topAppBar);

        FragmentManager fragmentManager = getSupportFragmentManager();

        configureToolbar();

        if (myProfileFragment == null) myProfileFragment = MyProfileFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, myProfileFragment)
                .commit();
    }

    private void configureToolbar() {
        toolbar.setTitle(getString(R.string.title_profile));
        toolbar.inflateMenu(R.menu.app_bar_consult_profile_menu);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        toolbar.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);

            return false;
        });
    }
}