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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by temp on 12/07/2021
 */
public class HomeUserViewHolder extends RecyclerView.ViewHolder {

    private TextView homeUserNameView;
    private TextView homeUserCityView;
    private ImageView homeUserProfilePictureView;

//    private ListView categriesNamesView;

//    private ArrayAdapter<String> categoriesAdapter;

//    private RecyclerView categoryRecyclerView ;
//    private HomeUserCategoryAdapter homeUserCategoryAdapter;
//    private List<Category> categories;

    public HomeUserViewHolder(@NonNull View itemView) {
        super(itemView);
        homeUserNameView = itemView.findViewById(R.id.text_profileName);
        homeUserCityView = itemView.findViewById(R.id.text_profileCity);
        homeUserProfilePictureView = itemView.findViewById(R.id.icon_profilePicture);
//        categriesNamesView = itemView.findViewById(R.id.categories_names_list);
    }

//    private void configureCategoryRecyclerView() {
//        categories = new ArrayList<>();
//        homeUserCategoryAdapter = new HomeUserCategoryAdapter(categories, Glide.with(this));
//        categoryRecyclerView.setAdapter(homeUserCategoryAdapter);
//        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//    }

    public void bind(User user, RequestManager glide) {
        /*homeUserNameView.setText(user.getPseudo());
        List<Address> addresses = user.getAddresses();
        Address firstAddress = addresses.get(0);
        homeUserCityView.setText(firstAddress.getCity());
        glide.load(user.getProfilePicture().getPath()).placeholder(R.drawable.ic_baseline_avatar_placeholder_24).centerCrop().into(homeUserProfilePictureView);*/
//        List<Skill> skills = user.getSkills();
//        List<String> categoriesNames = new ArrayList<>();
//        for (Skill skill : skills) {
//            Category category = skill.getCategory();
//            categoriesNames.add(category.getName());
//        }
//        ArrayAdapter categoriesAdapter = new ArrayAdapter<String>(this,R.layout.item_home_user,categoriesNames);
//        categriesNamesView.setAdapter(categoriesAdapter);
//        homeUserCategoryAdapter = new HomeUserCategoryAdapter(categories, Glide.with(this));
//        categriesNamesView.setAdapter(homeUserCategoryAdapter);
    }
}
