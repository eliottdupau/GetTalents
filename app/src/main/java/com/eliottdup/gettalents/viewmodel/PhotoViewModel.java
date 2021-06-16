package com.eliottdup.gettalents.viewmodel;

import com.eliottdup.gettalents.model.Photo;

import java.util.UUID;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PhotoViewModel extends ViewModel {
    private MutableLiveData<Photo> photo;

    public void setPhoto(Photo photo) {
        this.photo.setValue(photo);
    }

    public LiveData<Photo> getPhoto() {
        if (this.photo == null) {
            this.photo = new MutableLiveData<>();
        }

        createPhoto();

        return this.photo;
    }

    private void createPhoto() {
        this.photo.setValue(new Photo(UUID.randomUUID().toString()));
    }
}
