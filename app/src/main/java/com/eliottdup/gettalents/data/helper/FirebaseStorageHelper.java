package com.eliottdup.gettalents.data.helper;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import androidx.annotation.NonNull;

public class FirebaseStorageHelper {

    public static void uploadProfilePicture(String storageRef, String localPath) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference ref = storageReference.child(storageRef);

        Uri file = Uri.fromFile(new File(localPath));
        UploadTask uploadTask = ref.putFile(file);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getTask();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        });
    }

    public static void downloadProfilePicture(Context context, ImageView view, String storageRef) {
        StorageReference rootRef = FirebaseStorage.getInstance().getReference();
        StorageReference ref = rootRef.child(storageRef);

        ref.getBytes(1024*1024)
                .addOnSuccessListener(bytes -> Glide.with(context).load(bytes).centerCrop().into(view))
                .addOnFailureListener(e -> Log.e("ProfilePicture", e.getMessage()));
    }
}
