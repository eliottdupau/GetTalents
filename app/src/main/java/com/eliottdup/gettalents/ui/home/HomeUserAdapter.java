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
    private User loggedUser;

    public HomeUserAdapter(List<User> users, RequestManager glide, User loggedUser) {
        // the data are from the fragment
        this.users = users;
        this.glide = glide;
        this.loggedUser = loggedUser;
    }

    // configure the view holder
    @NonNull
    @Override
    public HomeUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_home_user, parent, false);

        return new HomeUserViewHolder(view);
    }

    // bind to the view holder
    @Override
    public void onBindViewHolder(@NonNull HomeUserViewHolder holder, int position) {
        holder.bind(this.users.get(position), this.glide);
        // pass the logged user data to the view holder
        holder.setLoggedUser(this.loggedUser);
    }

    // get the number of users
    @Override
    public int getItemCount() {
        return this.users.size();
    }

    // update the users list
    public void updateData(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    // get the index of the user
    public User getUser(int position) {
        return this.users.get(position);
    }

}
