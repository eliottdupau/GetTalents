package com.eliottdup.gettalents.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.profile.UserProfileActivity;
import com.google.android.material.button.MaterialButton;

public class SearchFragment extends Fragment {

    public SearchFragment() {}

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        MaterialButton tempBtn = root.findViewById(R.id.tempButton_profileFragment);

        tempBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), UserProfileActivity.class);
            startActivity(intent);
        });

        return root;
    }
}