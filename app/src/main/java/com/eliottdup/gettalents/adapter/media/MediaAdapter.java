package com.eliottdup.gettalents.adapter.media;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Picture;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MediaAdapter extends RecyclerView.Adapter<MediaViewHolder> {
    private List<Picture> pictures;
    private final RequestManager glide;

    public MediaAdapter(List<Picture> pictures, RequestManager glide) {
        this.pictures = pictures;
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
        holder.bind(pictures.get(position), glide);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public void updateMedia(List<Picture> pictures) {
        this.pictures = pictures;
        notifyDataSetChanged();
    }

    public Picture getPhoto(int position) {
        return this.pictures.get(position);
    }
}
