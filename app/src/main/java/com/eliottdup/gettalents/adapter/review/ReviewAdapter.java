package com.eliottdup.gettalents.adapter.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Review;
import com.eliottdup.gettalents.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    private User user;
    private List<Review> reviewList;
    private final RequestManager glide;

    public ReviewAdapter(List<Review> reviewList, RequestManager glide) {
        this.reviewList = reviewList;
        this.glide = glide;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_review, parent, false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bind(user, reviewList.get(position), glide);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public void updateData(User user, List<Review> reviewList) {
        this.user = user;
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }
}
