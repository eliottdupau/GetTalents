package com.eliottdup.gettalents.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Category;

import java.util.List;

/**
 * Created by temp on 20/06/2021
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    public interface ICategorySelect {
        void onCategorySelect(String categoryName);
    }

    private ICategorySelect mCallback;

    private List<Category> categories;
    private RequestManager glide;

    public CategoryAdapter(List<Category> categories, RequestManager glide, ICategorySelect callback) {
        this.categories = categories;
        this.glide = glide;
        mCallback = callback;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_category, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(this.categories.get(position), this.glide);
        holder.categoryTextView.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                String categoryName = holder.categoryTextView.getText().toString();
                if (holder.categoryTextView.getCurrentTextColor() == Color.parseColor("#26C6DA")) {
                    holder.categoryTextView.setTextColor(Color.parseColor("#795548"));
                } else {
                    holder.categoryTextView.setTextColor(Color.parseColor("#26C6DA"));
                }

                mCallback.onCategorySelect(categoryName);
            };
        });
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
