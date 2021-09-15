package com.eliottdup.gettalents.ui.home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Category;

public class HomeUserCategoryViewHolder extends RecyclerView.ViewHolder {
    private TextView categoryTextView;

    public HomeUserCategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryTextView = itemView.findViewById(R.id.textView_category);
    }

    public void bind(Category category, RequestManager glide) {
        categoryTextView.setText(category.getName());
    }

}
