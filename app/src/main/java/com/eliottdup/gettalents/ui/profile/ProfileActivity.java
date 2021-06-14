package com.eliottdup.gettalents.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.ui.profile.consult.MyProfileFragment;
import com.eliottdup.gettalents.ui.profile.consult.UserProfileFragment;
import com.eliottdup.gettalents.ui.profile.edit.EditProfileFragment;
import com.eliottdup.gettalents.viewmodel.UserViewModel;

public class ProfileActivity extends AppCompatActivity implements MyProfileFragment.OnButtonClickedListener, EditProfileFragment.OnButtonClickedListener, UserProfileFragment.OnButtonClickedListener {
    public static final String KEY_USER = "user";

    private FragmentManager fragmentManager;
    private MyProfileFragment myProfileFragment;
    private UserProfileFragment userProfileFragment;
    private EditProfileFragment editProfileFragment;

    private UserViewModel viewModel;

    private User userFromDB;
    private String userLoggedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userLoggedId = getIntent().getStringExtra(KEY_USER);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        getUser();

        fragmentManager = getSupportFragmentManager();

        configureDefaultFragment();
    }

    private void configureDefaultFragment() {
        if (userLoggedId.equals(userFromDB.getId())) {
            if (myProfileFragment == null) myProfileFragment = MyProfileFragment.newInstance();

            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, myProfileFragment)
                    .commit();
        } else {
            if (userProfileFragment == null) userProfileFragment = UserProfileFragment.newInstance();

            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, userProfileFragment)
                    .commit();
        }
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

    private void getUser() {
        userFromDB = viewModel.getUser().getValue();
    }
}