package com.eliottdup.gettalents.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.profile.consult.ConsultProfileFragment;
import com.eliottdup.gettalents.ui.profile.edit.EditProfileFragment;

public class ProfileActivity extends AppCompatActivity implements ConsultProfileFragment.OnButtonClickedListener, EditProfileFragment.OnButtonClickedListener {
    private FragmentManager fragmentManager;
    private ConsultProfileFragment consultProfileFragment;
    private EditProfileFragment editProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fragmentManager = getSupportFragmentManager();

        configureDefaultFragment();
    }

    private void configureDefaultFragment() {
        if (consultProfileFragment == null) consultProfileFragment = ConsultProfileFragment.newInstance();

        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, consultProfileFragment)
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
        if (consultProfileFragment == null) consultProfileFragment = ConsultProfileFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, consultProfileFragment)
                .addToBackStack(null)
                .commit();
    }
}