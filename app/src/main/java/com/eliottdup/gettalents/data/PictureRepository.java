package com.eliottdup.gettalents.data;

import android.util.Log;

import com.eliottdup.gettalents.model.Picture;

import androidx.lifecycle.MutableLiveData;

// Todo() : Rajouter les dernières méthodes CRUD
public class PictureRepository {

    public PictureRepository() {}

    // Todo() : Mettre à jour la méthode une fois le back créé
    public MutableLiveData<Picture> uploadPicture(Picture picture) {
        MutableLiveData<Picture> pictureMutableLiveData = new MutableLiveData<>();

        pictureMutableLiveData.setValue(picture);
        Log.d("Picture", "Upload -> success");

        return pictureMutableLiveData;
    }
}
