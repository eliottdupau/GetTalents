package com.eliottdup.gettalents.data.repository;

import android.util.Log;

import com.eliottdup.gettalents.data.RetrofitInstance;
import com.eliottdup.gettalents.data.service.PictureService;
import com.eliottdup.gettalents.data.service.UserService;
import com.eliottdup.gettalents.model.Picture;
import com.eliottdup.gettalents.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PictureRepository {

    public PictureRepository() { }

    public void createPicture(Picture picture) {
        PictureService pictureService = RetrofitInstance.getInstance().create(PictureService.class);
        Call<Void> call = pictureService.createPicture(picture);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("HTTP 201", "Picture Creation -> success");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("HTTP", "Picture Creation -> fail");
            }
        });
    }

    public MutableLiveData<Picture> getPictureById(int id) {
        MutableLiveData<Picture> pictureMutableLiveData = new MutableLiveData<>();

        PictureService pictureService = RetrofitInstance.getInstance().create(PictureService.class);
        Call<Picture> call = pictureService.getPictureById(id);
        call.enqueue(new Callback<Picture>() {
            @Override
            public void onResponse(@NonNull Call<Picture> call, @NonNull Response<Picture> response) {
                Log.d("HTTP 200", "Get Picture by Id -> success");
                pictureMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Picture> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "Picture Not Found");
            }
        });

        return pictureMutableLiveData;
    }

    public MutableLiveData<List<Picture>> getAllPictures() {
        MutableLiveData<List<Picture>> picturesMutableLiveData = new MutableLiveData<>();

        PictureService pictureService = RetrofitInstance.getInstance().create(PictureService.class);
        Call<List<Picture>> call = pictureService.getAllPictures();
        call.enqueue(new Callback<List<Picture>>() {
            @Override
            public void onResponse(@NonNull Call<List<Picture>> call, @NonNull Response<List<Picture>> response) {
                Log.d("HTTP 200", "Get Pictures -> success");
                picturesMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Picture>> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "Picture Not Found");
            }
        });

        return picturesMutableLiveData;
    }

    public void updatePicture(int id, Picture picture) {
        PictureService pictureService = RetrofitInstance.getInstance().create(PictureService.class);
        Call<Void> call = pictureService.updatePicture(id, picture);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("HTTP 204", "Update Picture -> success");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "Picture Not Found");
            }
        });
    }

    public void deletePicture(int id) {
        PictureService pictureService = RetrofitInstance.getInstance().create(PictureService.class);
        Call<Void> call = pictureService.deletePicture(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("HTTP 200", "Delete Picture -> success");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "Picture Not Found");
            }
        });
    }
}
