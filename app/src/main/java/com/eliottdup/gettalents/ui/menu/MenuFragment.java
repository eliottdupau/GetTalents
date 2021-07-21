package com.eliottdup.gettalents.ui.menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.authentication.AuthenticationActivity;
import com.eliottdup.gettalents.ui.favorite.FavoriteActivity;
import com.eliottdup.gettalents.ui.profile.MyProfileActivity;
import com.google.android.material.card.MaterialCardView;

public class MenuFragment extends Fragment {
    private MaterialCardView profileItem, favoriteItem, logoutItem;

    public MenuFragment() { }

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        profileItem = root.findViewById(R.id.cardView_profile);
        favoriteItem = root.findViewById(R.id.cardView_favorite);
        logoutItem = root.findViewById(R.id.cardView_logout);

        setupItemClick(profileItem);
        setupItemClick(favoriteItem);
        setupItemClick(logoutItem);

        return root;
    }

    private void setupItemClick(View view) {
        view.setOnClickListener(v -> startActivity(getIntent(view)));
    }

    @SuppressLint("NonConstantResourceId")
    private Intent getIntent(View view) {
        switch (view.getId()) {
            case R.id.cardView_profile:
                return new Intent(getContext(), MyProfileActivity.class);
            case R.id.cardView_favorite:
                return new Intent(getContext(), FavoriteActivity.class);
            case R.id.cardView_logout:
                return new Intent(getContext(), AuthenticationActivity.class);
            default:
                return new Intent();
        }
    }
}