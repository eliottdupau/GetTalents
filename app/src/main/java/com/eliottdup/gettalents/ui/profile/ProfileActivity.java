package com.eliottdup.gettalents.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.profile.consult.MyProfileFragment;
import com.eliottdup.gettalents.ui.profile.edit.EditProfileFragment;
import com.eliottdup.gettalents.viewmodel.UserViewModel;

public class ProfileActivity extends AppCompatActivity implements MyProfileFragment.OnButtonClickedListener, EditProfileFragment.OnButtonClickedListener {
    private FragmentManager fragmentManager;
    private MyProfileFragment myProfileFragment;
    private EditProfileFragment editProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        fragmentManager = getSupportFragmentManager();

        configureDefaultFragment();
    }

    private void configureDefaultFragment() {
        if (myProfileFragment == null) myProfileFragment = MyProfileFragment.newInstance();

        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, myProfileFragment)
                .commit();
    }

    @Override
    public void onBackButtonClicked() {
        onBackPressed();
    }

    @Override
    public void onEditProfileButtonClicked() {
        if (editProfileFragment == null) editProfileFragment = EditProfileFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, editProfileFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSaveChangesButtonClicked() {
        if (myProfileFragment == null) myProfileFragment = MyProfileFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, myProfileFragment)
                .addToBackStack(null)
                .commit();
    }
}