package com.eliottdup.gettalents.adapter.media;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Photo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MediaAdapter extends RecyclerView.Adapter<MediaViewHolder> {
    private List<Photo> photos;
    private final RequestManager glide;

    public MediaAdapter(List<Photo> photos, RequestManager glide) {
        this.photos = photos;
        this.glide = glide;
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_media, parent, false);

        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {
        holder.bind(photos.get(position), glide);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void updateMedia(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position) {
        return this.photos.get(position);
    }
}
