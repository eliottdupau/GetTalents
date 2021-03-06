package com.eliottdup.gettalents.viewmodel;

import android.app.Application;

import com.eliottdup.gettalents.data.repository.PictureRepository;
import com.eliottdup.gettalents.model.Picture;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PictureViewModel extends AndroidViewModel {
    private final PictureRepository pictureRepository;

    private MutableLiveData<Picture> picture = new MutableLiveData<>();

    public PictureViewModel(@NonNull Application application) {
        super(application);

        this.pictureRepository = new PictureRepository();
    }

    public void setPicture(Picture picture) {
        this.picture.setValue(picture);
    }

    public LiveData<Picture> getPicture() {
        return this.picture;
    }

    public void createPicture(Picture picture) {
        //pictureRepository.createPicture(picture);
    }
}
