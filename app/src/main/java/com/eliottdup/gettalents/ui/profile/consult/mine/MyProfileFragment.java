package com.eliottdup.gettalents.ui.profile.consult.mine;

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
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.utils.DateUtils;
import com.eliottdup.gettalents.viewmodel.MyProfileViewModel;

public class MyProfileFragment extends Fragment {
    private ImageView profilePicture;
    private TextView pseudoView, birthdayView, mailView;

    private MyProfileViewModel viewModel;

    public MyProfileFragment() { }

    public static MyProfileFragment newInstance() {
        return new MyProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_profile, container, false);

        profilePicture = root.findViewById(R.id.icon_profilePicture);
        pseudoView = root.findViewById(R.id.textView_pseudo);
        birthdayView = root.findViewById(R.id.textView_birthday);
        mailView = root.findViewById(R.id.textView_mail);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MyProfileViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();

        getUser();
    }

    private void getUser() {
        viewModel.getLoggedUser();
        viewModel.user.observe(getViewLifecycleOwner(), this::updateUI);
    }

    private void updateUI(User user) {
        Glide.with(this)
                .load(user.getProfilePicture().getPath())
                .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                .centerCrop()
                .into(profilePicture);

        pseudoView.setText(user.getPseudo());
        mailView.setText(user.getEmail());
        birthdayView.setText(DateUtils.formatDate(user.getBirthday()));
    }
}