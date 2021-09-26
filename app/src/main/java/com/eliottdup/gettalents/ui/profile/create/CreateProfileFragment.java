package com.eliottdup.gettalents.ui.profile.create;

import android.content.Context;
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
import com.eliottdup.gettalents.ui.profile.edit.BirthdayDialogFragment;
import com.eliottdup.gettalents.ui.profile.edit.PictureDialogFragment;
import com.eliottdup.gettalents.ui.profile.edit.PseudoDialogFragment;
import com.eliottdup.gettalents.utils.DateUtils;
import com.eliottdup.gettalents.viewmodel.EditProfileViewModel;
import com.eliottdup.gettalents.viewmodel.PictureViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class CreateProfileFragment extends Fragment {
    private MaterialCardView profilePictureCard, pseudoCard, birthdayCard;
    private MaterialButton validateButton, skipButton;
    private ImageView profilePicture;
    private TextView pseudoView, birthdayView;

    private EditProfileViewModel viewModel;
    private PictureViewModel pictureViewModel;

    private FragmentManager fragmentManager;

    private User user;

    private OnButtonClickedListener callback;

    public interface OnButtonClickedListener {
        void onSkillButtonClicked(User user);
        void onSkipButtonClicked();
    }

    public CreateProfileFragment() {}

    public static CreateProfileFragment newInstance() { return new CreateProfileFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_profile, container, false);

        validateButton = root.findViewById(R.id.btnValidate);
        skipButton = root.findViewById(R.id.btnSkip);
        profilePictureCard = root.findViewById(R.id.container_profilePicture);
        profilePicture = root.findViewById(R.id.icon_profilePicture);
        pseudoCard = root.findViewById(R.id.container_pseudo);
        birthdayCard = root.findViewById(R.id.container_birthday);
        pseudoView = root.findViewById(R.id.textView_pseudo);
        birthdayView = root.findViewById(R.id.textView_birthday);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(EditProfileViewModel.class);
        pictureViewModel = new ViewModelProvider(requireActivity()).get(PictureViewModel.class);

        fragmentManager = getParentFragmentManager();

        validateButton.setOnClickListener(v -> callback.onSkillButtonClicked(user));
        skipButton.setOnClickListener(v -> callback.onSkipButtonClicked());

        setupView();
        getUser();
        managePicture();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        createCallbackToParentActivity();
    }

    private void createCallbackToParentActivity() {
        callback = (OnButtonClickedListener) getActivity();
    }

    private void setupView() {
        profilePictureCard.setOnClickListener(view -> {
            PictureDialogFragment pictureDialogFragment = PictureDialogFragment.newInstance();
            pictureDialogFragment.show(fragmentManager, "photoFragment");
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
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            this.user = user;
            updateUI(user);
        });
    }

    private void managePicture() {
        pictureViewModel.getPicture().observe(getViewLifecycleOwner(), photo -> {
            if (photo.getPath() != null) {
                user.setProfilePicture(photo);

                Glide.with(this)
                        .load(user.getProfilePicture().getPath())
                        .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                        .centerCrop()
                        .into(profilePicture);
            }
        });
    }

    private void updateUI(User user) {
        if (user != null) {
            if (user.getProfilePicture() != null) {
                Glide.with(this)
                        .load(user.getProfilePicture().getPath())
                        .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                        .centerCrop()
                        .into(profilePicture);
            }

            if (user.getPseudo() != null) pseudoView.setText(user.getPseudo());
            if (user.getBirthday() != null) birthdayView.setText(DateUtils.formatDate(user.getBirthday()));
        }
    }
}