package com.eliottdup.gettalents.ui.review;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.adapter.media.MediaAdapter;
import com.eliottdup.gettalents.model.Review;
import com.eliottdup.gettalents.model.Picture;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.ui.profile.edit.PhotoDialogFragment;
import com.eliottdup.gettalents.utils.ItemClickSupport;
import com.eliottdup.gettalents.viewmodel.ReviewViewModel;
import com.eliottdup.gettalents.viewmodel.PictureViewModel;
import com.eliottdup.gettalents.viewmodel.UserViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {
    private MaterialToolbar toolbar;
    private RatingBar ratingBar;
    private TextInputLayout commentLayout;
    private TextInputEditText commentView;
    private MaterialCardView mediaButton;
    private RecyclerView recyclerView;

    private ReviewViewModel reviewViewModel;
    private UserViewModel userViewModel;
    private PictureViewModel pictureViewModel;

    private FragmentManager fragmentManager;

    private Review review;

    private MediaAdapter adapter;
    private List<Picture> pictureList;

    public ReviewFragment() {}

    public static ReviewFragment newInstance() {
        return new ReviewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_review, container, false);

        toolbar = root.findViewById(R.id.topAppBar);
        ratingBar = root.findViewById(R.id.ratingBar);
        commentLayout = root.findViewById(R.id.inputLayout_comment);
        commentView = root.findViewById(R.id.editText_comment);
        mediaButton = root.findViewById(R.id.container_addMedia);
        recyclerView = root.findViewById(R.id.recyclerView);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reviewViewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        pictureViewModel = new ViewModelProvider(requireActivity()).get(PictureViewModel.class);

        fragmentManager = getParentFragmentManager();

        getEvaluation();
        managePhoto();

        configureToolbar();
        configureRecyclerView();
        setupView();
    }

    private void getEvaluation() {
        review = reviewViewModel.getEvaluation().getValue();

        getUser();
    }

    private void getUser() {
        userViewModel.getLoggedUser();
        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            review.setUser(user);
        });
    }

    private void managePhoto() {
        pictureViewModel.getPicture().observe(getViewLifecycleOwner(), photo -> {
            if (photo.getUri() != null) {
                pictureList.add(photo);
                adapter.updateMedia(pictureList);
            }
        });
    }

    private void configureToolbar() {
        toolbar.setTitle(getString(R.string.title_evaluation));
        toolbar.inflateMenu(R.menu.app_bar_save_changes_menu);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_clear_24, null));
        toolbar.setNavigationOnClickListener(view -> requireActivity().onBackPressed());

        toolbar.setOnMenuItemClickListener(item -> {
            if (!isEmptyOrOversize(review.getComment())) {
                showValidationDialog();
            } else {
                if (review.getComment().isEmpty()) commentLayout.setError(getString(R.string.error_empty));
            }

            return false;
        });
    }

    private void configureRecyclerView() {
        pictureList = new ArrayList<>();
        adapter = new MediaAdapter(pictureList, Glide.with(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        ItemClickSupport.addTo(recyclerView, R.layout.item_media).setOnItemClickListener((recyclerView, position, v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle(getString(R.string.title_delete_media))
                    .setPositiveButton(getString(R.string.label_ok), (dialogInterface, i) -> {
                        Picture picture = adapter.getPhoto(position);
                        pictureList.remove(picture);
                        adapter.updateMedia(pictureList);
                    })
                    .setNegativeButton(getString(R.string.label_cancel), (dialogInterface, i) -> { })
                    .show();
        });
    }

    private void setupView() {
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, b) -> review.setRating(rating));
        review.setRating(ratingBar.getRating());

        commentView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence input, int i, int i1, int i2) {
                String comment = input.toString().trim();

                review.setComment(comment);

                if (isEmptyOrOversize(comment)) commentLayout.setError(getString(R.string.error_oversize));
                else commentLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        mediaButton.setOnClickListener(view -> {
            PhotoDialogFragment photoDialogFragment = PhotoDialogFragment.newInstance();
            photoDialogFragment.show(fragmentManager, "photoFragment");
        });
    }

    private boolean isEmptyOrOversize(String input) {
        return input.isEmpty() || input.length() > 200;
    }

    private void showValidationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getString(R.string.title_evaluation))
                .setMessage(getString(R.string.disclaimer_leave_appreciation))
                .setPositiveButton(getString(R.string.label_yes), (dialogInterface, i) -> {
                    review.setPictureList(pictureList);
                    reviewViewModel.setEvaluation(review);
                    requireActivity().onBackPressed();
                })
                .setNegativeButton(getString(R.string.label_cancel), (dialogInterface, i) -> {})
                .show();
    }
}