package com.eliottdup.gettalents.ui.home;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.Category;
import com.eliottdup.gettalents.model.Skill;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.utils.DistanceUtils;
import com.eliottdup.gettalents.viewmodel.HomeViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    private User loggedUser;

    public HomeUserViewHolder(@NonNull View itemView) {
        super(itemView);
        homeUserNameView = itemView.findViewById(R.id.text_profileName);
        homeUserCityView = itemView.findViewById(R.id.text_profileCity);
        homeUserProfilePictureView = itemView.findViewById(R.id.icon_profilePicture);
    }

    // retrieve the logged user data from the adapter
    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }

    // bind the user data with each part of the layout
    public void bind(User user, RequestManager glide) {

        // user image
        glide.load(user.getProfilePicture().getPath()).placeholder(R.drawable.ic_baseline_avatar_placeholder_24).centerCrop().into(homeUserProfilePictureView);

        // user name
        homeUserNameView.setText(user.getPseudo());

        // user location
        String cityDisplay = "-";

        if (user.getAddresses().size() > 0) {

            // get the city name from the first address
            List<Address> addresses = user.getAddresses();
            Address userfirstAddress = addresses.get(0);
            String userCityName = userfirstAddress.getCity();

            if (this.loggedUser != null) {

                // check if the logged user has an address and if it has coordinates
                if (this.loggedUser.getAddresses().size() > 0) {
                    if(this.loggedUser.getAddresses().get(0).getLat() != 0 && this.loggedUser.getAddresses().get(0).getLng() != 0 && userfirstAddress.getLat() != 0 && userfirstAddress.getLng() != 0) {

                        // both users coordinates from their first address
                        double latUser1 = Double.valueOf(this.loggedUser.getAddresses().get(0).getLat());
                        double lngUser1 = Double.valueOf(this.loggedUser.getAddresses().get(0).getLng());
                        double latUser2 = Double.valueOf(userfirstAddress.getLat());
                        double lngUser2 = Double.valueOf(userfirstAddress.getLng());
                        // get the distance in kms with the coordinates of each users
                        double distance = DistanceUtils.calculateDistance(latUser1, latUser2, lngUser1, lngUser2);
                        // change the display depending on the distance value
                        if (distance >= 10) {
                            DecimalFormat df = new DecimalFormat("###");
                            cityDisplay = userCityName + " (" + df.format(distance) + " kms)";
                        } else if (distance >= 2) {
                            DecimalFormat df = new DecimalFormat("###.#");
                            cityDisplay = userCityName + " (" + df.format(distance) + " kms)";
                        } else {
                            DecimalFormat df = new DecimalFormat("###.#");
                            cityDisplay = userCityName + " (" + df.format(distance) + " km)";
                        }
                    }
                } else {
                    cityDisplay = userCityName;
                }
            } else {
                cityDisplay = userCityName;
            }

        }
        homeUserCityView.setText(cityDisplay);

    }
}
