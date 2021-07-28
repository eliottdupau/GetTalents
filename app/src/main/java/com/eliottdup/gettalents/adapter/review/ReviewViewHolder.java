package com.eliottdup.gettalents.adapter.review;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.adapter.media.MediaAdapter;
import com.eliottdup.gettalents.model.Picture;
import com.eliottdup.gettalents.model.Review;
import com.eliottdup.gettalents.ui.picture.PictureActivity;
import com.eliottdup.gettalents.ui.profile.consult.other.UserProfileActivity;
import com.eliottdup.gettalents.utils.DateUtils;
import com.eliottdup.gettalents.utils.ItemClickSupport;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    private final TextView commentView, ratingView, pseudoView, publicationDateView;
    private final ConstraintLayout userContainer;
    private final ImageView profilePictureView;
    private final RecyclerView recyclerView;

    private List<Picture> pictureList;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);

        commentView = itemView.findViewById(R.id.textView_comment);
        ratingView = itemView.findViewById(R.id.textView_rating);
        pseudoView = itemView.findViewById(R.id.textView_pseudo);
        publicationDateView = itemView.findViewById(R.id.textView_publicationDate);
        userContainer = itemView.findViewById(R.id.userContainer);
        profilePictureView = itemView.findViewById(R.id.imageView_profilePicture);
        recyclerView = itemView.findViewById(R.id.recyclerView);
    }

    public void bind(Context context, Review review, RequestManager glide) {
        commentView.setText(review.getComment());
        ratingView.setText(String.format("%s", review.getRating()));
        pseudoView.setText(review.getUser().getPseudo());
        publicationDateView.setText("Publié le " + DateUtils.formatDate(review.getPublicationDate()));

        glide.load(review.getUser().getProfilePicture().getUri())
                .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                .centerCrop()
                .into(profilePictureView);

        userContainer.setOnClickListener(view -> {
            Intent intent = new Intent(context, UserProfileActivity.class);
            intent.putExtra(UserProfileActivity.KEY_USER_ID, review.getUser().getId());
            context.startActivity(intent);
        });

        pictureList = review.getPictureList();

        recyclerView.setVisibility(pictureList.isEmpty() ? View.GONE : View.VISIBLE);
        if (!pictureList.isEmpty()) {
            configureRecyclerView(context, glide);
        }
    }

    private void configureRecyclerView(Context context, RequestManager glide) {
        MediaAdapter adapter = new MediaAdapter(pictureList, glide);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), RecyclerView.HORIZONTAL, false));

        ItemClickSupport.addTo(recyclerView, R.layout.item_media).setOnItemClickListener((recyclerView, position, v) -> {
            Intent intent = new Intent(context, PictureActivity.class);
            intent.putExtra(PictureActivity.KEY_IMAGE_URI, pictureList.get(position).getUri());
            context.startActivity(intent);
        });
    }
}
