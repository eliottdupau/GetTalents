package com.eliottdup.gettalents.ui.profile.consult.other;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.User;

public class UserProfileActivity extends AppCompatActivity implements UserProfileFragment.OnButtonClickedListener {
    public static final String KEY_USER_ID = "userId";
    private UserProfileFragment userProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        String userId = getIntent().getStringExtra(KEY_USER_ID);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (userProfileFragment == null) userProfileFragment = UserProfileFragment.newInstance(userId);

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, userProfileFragment)
                .commit();
    }

    @Override
    public void onBackButtonClicked() {
        onBackPressed();
    }
}