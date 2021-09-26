package com.eliottdup.gettalents.ui.home;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Category;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by temp on 20/06/2021
 */
public class CategoryViewHolder extends RecyclerView.ViewHolder {
    public TextView categoryTextView;
    private ImageView categoryIconView;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryTextView = itemView.findViewById(R.id.textView_category);
        categoryIconView = itemView.findViewById(R.id.iconView_category);
    }

    // bind the category data with each part of the layout
    public void bind(Category category, RequestManager glide) {

        // category image
        glide.load(category.getCategoryPicture().getPath()).placeholder(R.drawable.ic_baseline_avatar_placeholder_24).centerCrop().into(categoryIconView);

        // category name
        categoryTextView.setText(category.getName());

    }

}