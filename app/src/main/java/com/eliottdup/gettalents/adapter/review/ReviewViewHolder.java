package com.eliottdup.gettalents.adapter.review;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Review;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.viewmodel.UserViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    private TextView commentView, ratingView, pseudoView;
    private ImageView profilePictureView;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);

        commentView = itemView.findViewById(R.id.textView_comment);
        ratingView = itemView.findViewById(R.id.textView_rating);
        pseudoView = itemView.findViewById(R.id.textView_pseudo);
        profilePictureView = itemView.findViewById(R.id.imageView_profilePicture);
    }

    public void bind(Review review, RequestManager glide) {
        commentView.setText(review.getComment());
        ratingView.setText(String.format("%s", review.getRating()));
        pseudoView.setText("SuperBebe");

        glide.load("https://torange.biz/photofxnew/76/HD/lion-profile-picture-76801.jpg")
                .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                .into(profilePictureView);
    }
}
