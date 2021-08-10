package com.eliottdup.gettalents.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.User;

import java.util.List;

/**
 * Created by temp on 12/07/2021
 */
public class HomeUserAdapter extends RecyclerView.Adapter<HomeUserViewHolder> {

    private List<User> users;
    private RequestManager glide;

    public HomeUserAdapter(List<User> users, RequestManager glide) {
        this.users = users;
        this.glide = glide;
    }

    @NonNull
    @Override
    public HomeUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_home_user, parent, false);

        return new HomeUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeUserViewHolder holder, int position) {
        holder.bind(this.users.get(position), this.glide);
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public void updateData(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public User getUser(int position) {
        return this.users.get(position);
    }

}
