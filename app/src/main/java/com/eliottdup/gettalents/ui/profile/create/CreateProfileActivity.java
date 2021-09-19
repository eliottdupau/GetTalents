package com.eliottdup.gettalents.ui.profile.create;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.ui.MainActivity;
import com.eliottdup.gettalents.ui.profile.edit.CreateProfileFragment;
import com.eliottdup.gettalents.ui.skills.CreateSkillsFragment;
import com.eliottdup.gettalents.utils.KeyUtils;
import com.eliottdup.gettalents.viewmodel.EditProfileViewModel;

public class CreateProfileActivity extends AppCompatActivity implements CreateProfileFragment.OnButtonClickedListener{
    private FragmentManager fragmentManager;
    private CreateProfileFragment createProfileFragment;
    private CreateSkillsFragment createSkillsFragment;

    private EditProfileViewModel viewModel;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        user = (User) getIntent().getSerializableExtra(KeyUtils.KEY_USER);

        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);

        fragmentManager = getSupportFragmentManager();

        if (createProfileFragment == null) createProfileFragment = CreateProfileFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, createProfileFragment)
                .commit();

        viewModel.setUser(user);
    }

    private void createUserInDB(User user) {
        viewModel.createUserInDB(user).observe(this, userInDB -> goToSkillFragment());
    }

    private void goToSkillFragment() {
        if (createSkillsFragment == null) createSkillsFragment = CreateSkillsFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, createSkillsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void goToMainActivity() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Skip first configuration ?")
                .setMessage("Don't worry you can fill your profile later")
                .setPositiveButton(R.string.label_ok, (dialogInterface, i) -> {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton(R.string.label_cancel, (dialogInterface, i) -> { })
                .show();
    }

    @Override
    public void onSkillButtonClicked(User user) {
        createUserInDB(user);
    }

    @Override
    public void onSkipButtonClicked() {
        goToMainActivity();
    }
}