package com.eliottdup.gettalents.ui.profile.create;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.ui.MainActivity;
import com.eliottdup.gettalents.utils.KeyUtils;
import com.eliottdup.gettalents.viewmodel.EditProfileViewModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class CreateProfileActivity extends AppCompatActivity implements CreateProfileFragment.OnButtonClickedListener{
    private CreateProfileFragment createProfileFragment;

    private EditProfileViewModel viewModel;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        user = (User) getIntent().getSerializableExtra(KeyUtils.KEY_USER);

        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (createProfileFragment == null) createProfileFragment = CreateProfileFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, createProfileFragment)
                .commit();

        viewModel.setUser(user);
    }

    private void uploadPicture(String ref) {
        StorageReference rootRef = FirebaseStorage.getInstance().getReference();
        StorageReference pictureRef = rootRef.child(ref);

        Uri file = Uri.fromFile(new File(user.getProfilePicture().getPath()));
        UploadTask uploadTask = pictureRef.putFile(file);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            user.getProfilePicture().setPath("images/profile/" + user.getFirebaseUid());
            createUserInDB(user);
        }).addOnFailureListener(e -> Log.e("Profilepicture", e.getMessage()));
    }

    private void createUserInDB(User user) {
        viewModel.createUserInDB(user).observe(this, userInDB -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
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
        this.user = user;
        uploadPicture("images/profile/" + user.getFirebaseUid());
    }

    @Override
    public void onSkipButtonClicked() {
        goToMainActivity();
    }
}