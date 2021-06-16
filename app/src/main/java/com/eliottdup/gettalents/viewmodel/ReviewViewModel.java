package com.eliottdup.gettalents.viewmodel;

import com.eliottdup.gettalents.model.Review;

import java.util.UUID;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReviewViewModel extends ViewModel {
    private MutableLiveData<Review> evaluation;

    public LiveData<Review> getEvaluation() {
        if (evaluation == null) {
            evaluation = new MutableLiveData<>();

            evaluation.setValue(new Review(UUID.randomUUID().toString()));
        }

        return evaluation;
    }

    public void setEvaluation(Review review) {
        this.evaluation.setValue(review);
    }
}
