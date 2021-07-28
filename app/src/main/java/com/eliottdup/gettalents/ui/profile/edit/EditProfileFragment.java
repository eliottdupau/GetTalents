package com.eliottdup.gettalents.ui.profile.edit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.eliottdup.gettalents.viewmodel.PictureViewModel;
import com.eliottdup.gettalents.viewmodel.UserViewModel;
import com.google.android.material.card.MaterialCardView;

public class EditProfileFragment extends Fragment {
    private ImageView profilePicture;
    private MaterialCardView profilePictureCard, pseudoCard, birthdayCard;
    private TextView pseudoView, birthdayView, mailView;

    private UserViewModel userViewModel;
    private PictureViewModel pictureViewModel;

    private FragmentManager fragmentManager;

    private User user;
    private String oldUri = "";

    public EditProfileFragment() { }

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        profilePictureCard = root.findViewById(R.id.container_profilePicture);
        profilePicture = root.findViewById(R.id.icon_profilePicture);
        pseudoCard = root.findViewById(R.id.container_pseudo);
        birthdayCard = root.findViewById(R.id.container_birthday);
        pseudoView = root.findViewById(R.id.textView_pseudo);
        birthdayView = root.findViewById(R.id.textView_birthday);
        mailView = root.findViewById(R.id.textView_mail);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        pictureViewModel = new ViewModelProvider(requireActivity()).get(PictureViewModel.class);

        fragmentManager = getParentFragmentManager();

        setupView();
        getUser();
        getProfilePicture();
    }

    private void setupView() {
        profilePictureCard.setOnClickListener(view -> {
            oldUri = user.getProfilePicture().getUri();
            PhotoDialogFragment photoDialogFragment = PhotoDialogFragment.newInstance();
            photoDialogFragment.show(fragmentManager, "photoFragment");
        });

        pseudoCard.setOnClickListener(view -> {
            PseudoDialogFragment pseudoDialogFragment = PseudoDialogFragment.newInstance();
            pseudoDialogFragment.show(fragmentManager, "pseudoFragment");
        });

        birthdayCard.setOnClickListener(view -> {
            BirthdayDialogFragment birthdayDialogFragment = BirthdayDialogFragment.newInstance();
            birthdayDialogFragment.show(fragmentManager, "birthdayFragment");
        });
    }

    private void getUser() {
        userViewModel.getLoggedUser();
        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            this.user = user;
            updateUI(user);
        });
    }

    private void getProfilePicture() {
        pictureViewModel.getPicture().observe(getViewLifecycleOwner(), picture -> {
            this.user.setProfilePicture(picture);

            Glide.with(this)
                    .load(picture.getUri())
                    .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                    .into(profilePicture);
        });
    }

    private void updateUI(User user) {
        Glide.with(this)
                .load(user.getProfilePicture().getUri())
                .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                .into(profilePicture);

        pseudoView.setText(user.getPseudo());
        mailView.setText(user.getMail());

        birthdayView.setText(DateUtils.formatDate(user.getBirthday()));
    }

    public void updateUser() {
        userViewModel.updateUser(user.getId(), user);

        // Todo() : Upload la/les photo sur le serveur de stockage des photos
        if (!oldUri.equals(user.getProfilePicture().getUri())) {
            pictureViewModel.createPicture(user.getProfilePicture());
        }
    }
}