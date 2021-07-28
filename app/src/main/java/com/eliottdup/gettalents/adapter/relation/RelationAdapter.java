package com.eliottdup.gettalents.adapter.relation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RelationAdapter extends RecyclerView.Adapter<RelationViewHolder> {
    private List<User> relationList;
    private RequestManager glide;

    public RelationAdapter(List<User> relationList, RequestManager glide) {
        this.relationList = relationList;
        this.glide = glide;
    }

    @NonNull
    @Override
    public RelationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_relation, parent, false);

        return new RelationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelationViewHolder holder, int position) {
        holder.bindData(relationList.get(position), glide);
    }

    @Override
    public int getItemCount() {
        return relationList.size();
    }

    public void updateData(List<User> relationList) {
        this.relationList = relationList;
        notifyDataSetChanged();
    }

    public User getUser(int position) {
        return relationList.get(position);
    }
}
