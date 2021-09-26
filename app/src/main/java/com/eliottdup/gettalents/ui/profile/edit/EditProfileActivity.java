package com.eliottdup.gettalents.ui.profile.edit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.viewmodel.EditProfileViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class EditProfileActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;

    private EditProfileViewModel viewModel;
    private User user;

    private EditProfileFragment editProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbar = findViewById(R.id.topAppBar);

        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);

        FragmentManager fragmentManager = getSupportFragmentManager();

        configureToolbar();

        getData();

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
            openDialog();

            return false;
        });
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.label_save_changes))
                .setPositiveButton(R.string.label_ok, (dialogInterface, i) -> {
                    uploadPicture("images/profile/" + user.getFirebaseUid());
                    finish();
                })
                .setNegativeButton(R.string.label_cancel, (dialogInterface, i) -> { })
                .show();
    }

    private void uploadPicture(String ref) {
        StorageReference rootRef = FirebaseStorage.getInstance().getReference();
        StorageReference pictureRef = rootRef.child(ref);

        Uri file = Uri.fromFile(new File(user.getProfilePicture().getPath()));
        UploadTask uploadTask = pictureRef.putFile(file);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            user.getProfilePicture().setPath("images/profile/" + user.getFirebaseUid());
            viewModel.updateUser(user.getFirebaseUid(), user);
        }).addOnFailureListener(e -> Log.e("Profilepicture", e.getMessage()));
    }

    private void getData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            viewModel.getLoggedUser(user.getUid());
            viewModel.getUser().observe(this, u -> this.user = u);
        }
    }
}