package com.eliottdup.gettalents.ui.profile.consult.other;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.ui.chat.ChatActivity;
import com.eliottdup.gettalents.ui.review.ReviewActivity;
import com.eliottdup.gettalents.viewmodel.UserProfileViewModel;
import com.google.android.material.card.MaterialCardView;

public class UserProfileFragment extends Fragment {
    public static final String KEY_USER_ID = "userId";

    private ImageView profilePicture, favoriteIcon;
    private TextView pseudoView, addressView;
    private MaterialCardView evaluateButton, chatButton, favoriteButton;

    private UserProfileViewModel viewModel;

    private User user;

    public UserProfileFragment() {}

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_profile, container, false);

        profilePicture = root.findViewById(R.id.icon_profilePicture);
        pseudoView = root.findViewById(R.id.textView_pseudo);
        addressView = root.findViewById(R.id.textView_address);
        evaluateButton = root.findViewById(R.id.container_evaluation);
        chatButton = root.findViewById(R.id.container_chat);
        favoriteButton = root.findViewById(R.id.container_favorite);
        favoriteIcon = root.findViewById(R.id.icon_favorite);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(UserProfileViewModel.class);

        setupView();

        getUser();
    }

    private void setupView() {
        evaluateButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ReviewActivity.class);
            intent.putExtra(KEY_USER_ID, user.getId());
            startActivity(intent);
        });

        chatButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ChatActivity.class);
            startActivity(intent);
        });

        favoriteButton.setOnClickListener(view -> {
            /*manageRelation(user.isInFavorite(user.getId()));

            updateFavoriteView(user.isInFavorite(user.getId()));*/
        });
    }

    private void getUser() {
        viewModel.user.observe(getViewLifecycleOwner(), user -> {
            this.user = user;
            updateUI(this.user);
        });
    }

    private void updateUI(User user) {
        Glide.with(this)
                .load(user.getProfilePicture().getPath())
                .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                .centerCrop()
                .into(profilePicture);

        pseudoView.setText(user.getPseudo());

        Address mainAddress = user.getAddresses().get(0);
        addressView.setText(String.format("%s, %s", mainAddress.getCity(), mainAddress.getCountry()));

        //updateFavoriteView(user.isInFavorite(user.getId()));
    }

    /* void manageRelation(boolean isInFavorite) {
        if (isInFavorite) user.getRelationList().remove(user);
        else user.getRelationList().add(user);
    }

    private void updateFavoriteView(boolean isInFavorite) {
        Glide.with(this)
                .load(isInFavorite ? R.drawable.ic_baseline_filled_like_24 : R.drawable.ic_baseline_like_24)
                .into(favoriteIcon);
    }*/
}