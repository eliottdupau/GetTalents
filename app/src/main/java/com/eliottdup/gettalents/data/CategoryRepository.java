package com.eliottdup.gettalents.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.eliottdup.gettalents.model.Category;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private final List<Category> categoryList;

    public CategoryRepository() {
        this.categoryList = new ArrayList<>();
    }

    public MutableLiveData<List<Category>> getCategories() {
        MutableLiveData<List<Category>> categoriesMutableLiveData = new MutableLiveData<>();

        CategoryService categoryService = RetrofitInstance.getInstance().create(CategoryService.class);
        Call<List<Category>> call = categoryService.getAllCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                Log.d("HTTP 200", "Get All Categories -> success");
                categoriesMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                Log.d("HTTP 404", "Category Not Found");
            }
        });

        return categoriesMutableLiveData;
    }

    public MutableLiveData<Category> getCategoryById(String id) {
        MutableLiveData<Category> categoryMutableLiveData = new MutableLiveData<>();

        CategoryService categoryService = RetrofitInstance.getInstance().create(CategoryService.class);
        Call<Category> call = categoryService.getCategoryById(id);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(@NonNull Call<Category> call, @NonNull Response<Category> response) {
                Log.d("HTTP 200", "Get Category by id -> success");
                categoryMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Category> call, @NonNull Throwable t) {
                Log.d("HTTP 404", "Category Not Found");
            }
        });

        return categoryMutableLiveData;
    }

}
