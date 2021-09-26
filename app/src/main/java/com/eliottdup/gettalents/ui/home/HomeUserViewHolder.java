package com.eliottdup.gettalents.ui.home;

import android.content.Context;
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
import com.eliottdup.gettalents.data.helper.FirebaseStorageHelper;
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

public class HomeUserViewHolder extends RecyclerView.ViewHolder {

    private final TextView homeUserNameView;
    private final TextView homeUserCityView;
    private final ImageView homeUserProfilePictureView;

    public HomeUserViewHolder(@NonNull View itemView) {
        super(itemView);
        homeUserNameView = itemView.findViewById(R.id.text_profileName);
        homeUserCityView = itemView.findViewById(R.id.text_profileCity);
        homeUserProfilePictureView = itemView.findViewById(R.id.icon_profilePicture);
    }

    // bind the user data with each part of the layout
    public void bind(Context context, User user, User loggedUser) {

        // user image
        FirebaseStorageHelper.downloadProfilePicture(
                context,
                homeUserProfilePictureView,
                user.getProfilePicture().getPath());

        // user name
        homeUserNameView.setText(user.getPseudo());

        // user location
        String cityDisplay = "-";

        if (user.getAddresses().size() > 0) {

            // get the city name from the first address
            List<Address> addresses = user.getAddresses();
            Address userfirstAddress = addresses.get(0);
            String userCityName = userfirstAddress.getCity() + ", " + userfirstAddress.getCountry();

            if (loggedUser != null) {

                // check if the logged user has an address and if it has coordinates
                if (loggedUser.getAddresses().size() > 0) {
                    if(loggedUser.getAddresses().get(0).getLat() != 0 && loggedUser.getAddresses().get(0).getLng() != 0 && userfirstAddress.getLat() != 0 && userfirstAddress.getLng() != 0) {

                        // both users coordinates from their first address
                        double latUser1 = loggedUser.getAddresses().get(0).getLat();
                        double lngUser1 = loggedUser.getAddresses().get(0).getLng();
                        double latUser2 = userfirstAddress.getLat();
                        double lngUser2 = userfirstAddress.getLng();
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
