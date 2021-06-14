package com.eliottdup.gettalents.ui.profile.edit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.viewmodel.UserViewModel;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class PhotoDialogFragment extends DialogFragment {
    private static final int RC_IMAGE_CAPTURE = 1;
    private static final int RC_IMAGE_PICK = 2;

    private MaterialButton cameraButton, mediaButton;

    private UserViewModel viewModel;

    private User user;
    private String currentPhotoPath;

    public PhotoDialogFragment() {}

    public static PhotoDialogFragment newInstance() {
        return new PhotoDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_photo_dialog, container, false);

        cameraButton = root.findViewById(R.id.button_camera);
        mediaButton = root.findViewById(R.id.button_media);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        getUser();
        setupView();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    private void getUser() {
        user = viewModel.getUser().getValue();
    }

    private void setupView() {
        cameraButton.setOnClickListener(view -> dispatchTakePictureIntent(getContext()));

        mediaButton.setOnClickListener(view -> {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto , RC_IMAGE_PICK);
        });
    }

    private void dispatchTakePictureIntent(Context context) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile(context);
        } catch (IOException ex) {
            Log.e("photoFile", ex.getMessage());
        }
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(context,
                    "com.example.android.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, RC_IMAGE_CAPTURE);
        }
    }

    private File createImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRENCH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            user.setUrlProfilePicture(currentPhotoPath);
            viewModel.setUser(user);
        }
        else if (requestCode == RC_IMAGE_PICK && resultCode == RESULT_OK) {
            currentPhotoPath =  data.getData().toString();
            user.setUrlProfilePicture(currentPhotoPath);
            viewModel.setUser(user);
        }

        dismiss();
    }
}