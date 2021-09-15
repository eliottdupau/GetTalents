package com.eliottdup.gettalents.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Category;

import java.util.List;

public class HomeUserCategoryAdapter extends RecyclerView.Adapter<HomeUserCategoryViewHolder> {
    private List<Category> categories;
    private RequestManager glide;

    public HomeUserCategoryAdapter(List<Category> categories, RequestManager glide) {
        this.categories = categories;
        this.glide = glide;
    }

    @NonNull
    @Override
    public HomeUserCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_category, parent, false);

        return new HomeUserCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeUserCategoryViewHolder holder, int position) {
        holder.bind(this.categories.get(position), this.glide);
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }

    public void updateData(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public Category getCategory(int position) {
        return this.categories.get(position);
    }

}
