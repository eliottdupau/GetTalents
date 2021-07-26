package com.eliottdup.gettalents.ui.profile.edit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.google.android.material.appbar.MaterialToolbar;

public class EditProfileActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;

    private EditProfileFragment editProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbar = findViewById(R.id.topAppBar);

        FragmentManager fragmentManager = getSupportFragmentManager();

        configureToolbar();

        if (editProfileFragment == null) editProfileFragment = EditProfileFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, editProfileFragment)
                .commit();
    }

    private void configureToolbar() {
        toolbar.setTitle(getString(R.string.title_edit_profile));
        toolbar.inflateMenu(R.menu.app_bar_save_changes_menu);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        toolbar.setOnMenuItemClickListener(item -> {
            editProfileFragment.updateUser();
            finish();

            return false;
        });
    }
}