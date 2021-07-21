package com.eliottdup.gettalents.adapter.media;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Picture;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MediaViewHolder extends RecyclerView.ViewHolder {
    private ImageView photoView;

    public MediaViewHolder(@NonNull View itemView) {
        super(itemView);

        photoView = itemView.findViewById(R.id.imageView_photo);
    }

    public void bind(Picture picture, RequestManager glide) {
        glide.load(picture.getUri())
                .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                .centerCrop()
                .into(photoView);
    }
}
