package com.eliottdup.gettalents.ui.profile.consult;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.adapter.review.ReviewAdapter;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.Review;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.ui.chat.ChatActivity;
import com.eliottdup.gettalents.ui.review.ReviewActivity;
import com.eliottdup.gettalents.viewmodel.UserViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class UserProfileFragment extends Fragment {
    private MaterialToolbar toolbar;
    private ImageView profilePicture, favoriteIcon;
    private TextView pseudoView, addressView;
    private MaterialCardView evaluateButton, chatButton, favoriteButton;
    private RecyclerView recyclerView;

    private UserViewModel viewModel;

    private User user;

    private ReviewAdapter adapter;
    private List<Review> reviewList;

    public OnButtonClickedListener callback;

    public interface OnButtonClickedListener {
        void onBackButtonClicked();
    }

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

        toolbar = root.findViewById(R.id.topAppBar);
        profilePicture = root.findViewById(R.id.icon_profilePicture);
        pseudoView = root.findViewById(R.id.textView_pseudo);
        addressView = root.findViewById(R.id.textView_address);
        evaluateButton = root.findViewById(R.id.container_evaluation);
        chatButton = root.findViewById(R.id.container_chat);
        favoriteButton = root.findViewById(R.id.container_favorite);
        favoriteIcon = root.findViewById(R.id.icon_favorite);
        recyclerView = root.findViewById(R.id.recyclerView_review);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        getUser();

        configureToolbar();
        configureRecyclerView();

        setupView();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.createCallbackToParentActivity();
    }

    private void createCallbackToParentActivity() {
        callback = (OnButtonClickedListener) getActivity();
    }

    private void getUser() {
        user = viewModel.getUser().getValue();
    }

    private void configureToolbar() {
        toolbar.setTitle(getString(R.string.title_other_profile));
        toolbar.setNavigationOnClickListener(view -> callback.onBackButtonClicked());
    }

    private void configureRecyclerView() {
        //reviewList = new ArrayList<>();
        reviewList = user.getReviewList();
        adapter = new ReviewAdapter(reviewList, Glide.with(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupView() {
        Glide.with(this)
                .load(user.getUrlProfilePicture())
                .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                .into(profilePicture);

        pseudoView.setText(user.getPseudo());

        Address mainAddress = user.getAddresses().get(0);
        addressView.setText(String.format("%s, %s", mainAddress.getCity(), mainAddress.getCountry()));

        updateFavoriteView(user.isInFavorite(user.getId()));

        evaluateButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ReviewActivity.class);
            startActivity(intent);
        });

        chatButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ChatActivity.class);
            startActivity(intent);
        });

        favoriteButton.setOnClickListener(view -> {
            manageRelation(user.isInFavorite(user.getId()));

            updateFavoriteView(user.isInFavorite(user.getId()));
        });
    }

    private void manageRelation(boolean isInFavorite) {
        if (isInFavorite) user.getRelationListId().remove(user.getId());
        else user.getRelationListId().add(user.getId());
    }

    private void updateFavoriteView(boolean isInFavorite) {
        Glide.with(this)
                .load(isInFavorite ? R.drawable.ic_baseline_filled_like_24 : R.drawable.ic_baseline_like_24)
                .into(favoriteIcon);
    }
}