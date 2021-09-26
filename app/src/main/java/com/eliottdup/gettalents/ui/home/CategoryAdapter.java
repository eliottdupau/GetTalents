package com.eliottdup.gettalents.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Category;

import java.util.List;

/**
 * Created by temp on 20/06/2021
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    // event listener callback for the category name click
    public interface ICategorySelect {
        void onCategorySelect(String categoryName);
    }

    private ICategorySelect iCategorySelect;

    private Context context;
    private List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories, ICategorySelect callback) {
        this.categories = categories;
        this.context = context;
        iCategorySelect = callback;
    }

    // configure the view holder
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_category, parent, false);

        return new CategoryViewHolder(view);
    }

    // bind to the view holder
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(context, this.categories.get(position));

        // set an event listener on the category name
        holder.categoryTextView.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                // change the color of the category name depending on whether the category is selected or not
                String categoryName = holder.categoryTextView.getText().toString();
                if (holder.categoryTextView.getCurrentTextColor() == Color.parseColor("#26C6DA")) {
                    holder.categoryTextView.setTextColor(Color.parseColor("#795548"));
                } else {
                    holder.categoryTextView.setTextColor(Color.parseColor("#26C6DA"));
                }
                // launch the reaction of this event from the fragment
                iCategorySelect.onCategorySelect(categoryName);
            };
        });
    }

    // get the number of categories
    @Override
    public int getItemCount() {
        return this.categories.size();
    }

    // update the categories list
    public void updateData(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    // get the index of the user
    public Category getCategory(int position) {
        return this.categories.get(position);
    }

}
