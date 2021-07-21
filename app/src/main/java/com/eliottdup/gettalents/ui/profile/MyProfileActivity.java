package com.eliottdup.gettalents.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.ui.profile.consult.MyProfileFragment;
import com.eliottdup.gettalents.ui.profile.edit.EditProfileFragment;

public class MyProfileActivity extends AppCompatActivity implements MyProfileFragment.OnButtonClickedListener, EditProfileFragment.OnButtonClickedListener {
    private FragmentManager fragmentManager;
    private MyProfileFragment myProfileFragment;
    private EditProfileFragment editProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        fragmentManager = getSupportFragmentManager();

        if (myProfileFragment == null) myProfileFragment = MyProfileFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, myProfileFragment)
                .commit();
    }

    private void configureFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackClicked() {
        onBackPressed();
    }

    @Override
    public void onEditClicked() {
        if (editProfileFragment == null) editProfileFragment = EditProfileFragment.newInstance();
        configureFragment(editProfileFragment);
    }

    @Override
    public void onSaveClicked(User user) {
        if (myProfileFragment == null) myProfileFragment = MyProfileFragment.newInstance();
        configureFragment(myProfileFragment);
    }
}