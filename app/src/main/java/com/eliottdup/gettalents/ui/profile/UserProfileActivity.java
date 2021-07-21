package com.eliottdup.gettalents.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.profile.consult.UserProfileFragment;

public class UserProfileActivity extends AppCompatActivity implements UserProfileFragment.OnButtonClickedListener {
    private UserProfileFragment userProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (userProfileFragment == null) userProfileFragment = UserProfileFragment.newInstance("1");

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, userProfileFragment)
                .commit();
    }

    @Override
    public void onBackButtonClicked() {
        onBackPressed();
    }
}