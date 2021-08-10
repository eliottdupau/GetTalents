package com.eliottdup.gettalents.ui.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.User;

import java.util.List;

/**
 * Created by temp on 12/07/2021
 */
public class HomeUserViewHolder extends RecyclerView.ViewHolder {

    private TextView homeUserNameView;
    private TextView homeUserCityView;
    private ImageView homeUserProfilePictureView;

    public HomeUserViewHolder(@NonNull View itemView) {
        super(itemView);
        homeUserNameView = itemView.findViewById(R.id.text_profileName);
        homeUserCityView = itemView.findViewById(R.id.text_profileCity);
        homeUserProfilePictureView = itemView.findViewById(R.id.icon_profilePicture);
    }

    public void bind(User user, RequestManager glide) {
        homeUserNameView.setText(user.getPseudo());
        List<Address> addresses = user.getAddresses();
        Address firstAddress = addresses.get(0);
        homeUserCityView.setText(firstAddress.getCity());
        glide.load(user.getUrlProfilePicture()).placeholder(R.drawable.ic_baseline_avatar_placeholder_24).centerCrop().into(homeUserProfilePictureView);
    }
}
