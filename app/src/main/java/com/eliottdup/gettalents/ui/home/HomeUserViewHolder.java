package com.eliottdup.gettalents.ui.home;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.Category;
import com.eliottdup.gettalents.model.Skill;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.viewmodel.HomeViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
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

    public double calculateDistance(double lat1, double lat2, double lng1, double lng2) {
        if ((lat1 == lat2) && (lng1 == lng2)) {
            return 0;
        }
        else {
            double theta = lng1 - lng2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            // convert to kms
            dist = dist * 1.609344;
            return (dist);
        }
    }

    public void bind(User user, RequestManager glide) {
        homeUserNameView.setText(user.getPseudo());
        List<Address> addresses = user.getAddresses();
        Address firstAddress = addresses.get(0);
        String cityName = firstAddress.getCity();
        double latUser1 = 50.883331;
        double lngUser1 = 1.66667;
        double latUser2 = Double.valueOf(firstAddress.getLat());
        double lngUser2 = Double.valueOf(firstAddress.getLng());
        double distance = calculateDistance(latUser1, latUser2, lngUser1, lngUser2);
        String cityDisplay = "";
        if (distance >= 10) {
            DecimalFormat df = new DecimalFormat("###");
            cityDisplay = cityName + " (" + df.format(distance) + " kms)";
        } else if (distance >= 2) {
            DecimalFormat df = new DecimalFormat("###.#");
            cityDisplay = cityName + " (" + df.format(distance) + " kms)";
        } else {
            DecimalFormat df = new DecimalFormat("###.#");
            cityDisplay = cityName + " (" + df.format(distance) + " km)";
        }
        homeUserCityView.setText(cityDisplay);
        glide.load(user.getProfilePicture().getPath()).placeholder(R.drawable.ic_baseline_avatar_placeholder_24).centerCrop().into(homeUserProfilePictureView);

    }
}
