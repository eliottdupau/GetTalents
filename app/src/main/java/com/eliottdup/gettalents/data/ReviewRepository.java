package com.eliottdup.gettalents.data;

import android.util.Log;

import com.eliottdup.gettalents.model.Review;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class ReviewRepository {

    public ReviewRepository() { }

    // Todo() : Mettre à jour la méthode une fois le back créé
    public MutableLiveData<Review> createReview(Review review) {
        MutableLiveData<Review> reviewMutableLiveData = new MutableLiveData<>();

        reviewMutableLiveData.setValue(review);
        Log.d("HTTP 201", "Create Review -> success");

        return reviewMutableLiveData;
    }
}
