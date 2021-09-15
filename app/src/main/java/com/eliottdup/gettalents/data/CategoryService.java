package com.eliottdup.gettalents.data;

import com.eliottdup.gettalents.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryService {

    @GET("categories")
    Call<List<Category>> getAllCategories();

    @GET("categories/{id}")
    Call<Category> getCategoryById(@Path("id") String id);

}
