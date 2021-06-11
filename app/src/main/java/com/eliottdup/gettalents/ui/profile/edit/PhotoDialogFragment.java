package com.eliottdup.gettalents.ui.profile.edit;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eliottdup.gettalents.R;
import com.google.android.material.button.MaterialButton;

import static android.app.Activity.RESULT_OK;

public class PhotoDialogFragment extends DialogFragment {
    private static final int RC_IMAGE_CAPTURE = 1;
    private static final int RC_IMAGE_PICK = 2;

    private MaterialButton cameraButton, mediaButton;

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

        initView();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    private void initView() {
        cameraButton.setOnClickListener(view -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, RC_IMAGE_CAPTURE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), "No Camera to display", Toast.LENGTH_SHORT).show();
            }
        });

        mediaButton.setOnClickListener(view -> {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto , RC_IMAGE_PICK);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");

            Toast.makeText(getContext(), "" + bitmap.toString(), Toast.LENGTH_SHORT).show();
        } else if (requestCode == RC_IMAGE_PICK && resultCode == RESULT_OK) {
            Uri selectedImage =  data.getData();

            Toast.makeText(getContext(), "" + selectedImage.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}