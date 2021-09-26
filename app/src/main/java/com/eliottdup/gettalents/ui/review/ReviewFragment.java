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
import com.eliottdup.gettalents.ui.profile.edit.PictureDialogFragment;
import com.eliottdup.gettalents.utils.DateUtils;
import com.eliottdup.gettalents.utils.ItemClickSupport;
import com.eliottdup.gettalents.viewmodel.ReviewViewModel;
import com.eliottdup.gettalents.viewmodel.PictureViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReviewFragment extends Fragment {
    public static final String KEY_USER_ID = "userId";

    private MaterialToolbar toolbar;
    private RatingBar ratingBar;
    private TextInputLayout titleLayout, commentLayout;
    private TextInputEditText titleView, commentView;
    private MaterialCardView mediaButton;
    private RecyclerView recyclerView;

    private ReviewViewModel reviewViewModel;
    private PictureViewModel pictureViewModel;

    private FragmentManager fragmentManager;

    private Review review;
    private int recipientId;

    private MediaAdapter adapter;
    private List<Picture> pictureList;

    public ReviewFragment() {}

    public static ReviewFragment newInstance(int recipientId) {
        ReviewFragment reviewFragment = new ReviewFragment();

        Bundle args = new Bundle();
        args.putSerializable(KEY_USER_ID, recipientId);
        reviewFragment.setArguments(args);

        return reviewFragment;
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
        titleLayout = root.findViewById(R.id.inputLayout_title);
        commentLayout = root.findViewById(R.id.inputLayout_comment);
        titleView = root.findViewById(R.id.editText_title);
        commentView = root.findViewById(R.id.editText_comment);
        mediaButton = root.findViewById(R.id.container_addMedia);
        recyclerView = root.findViewById(R.id.recyclerView);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            recipientId = getArguments().getInt(KEY_USER_ID);
        }

        reviewViewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);
        pictureViewModel = new ViewModelProvider(requireActivity()).get(PictureViewModel.class);

        fragmentManager = getParentFragmentManager();

        initReview();
        getUser();
        managePicture();

        configureToolbar();
        configureRecyclerView();
        setupView();
    }

    private void initReview() {
        review = reviewViewModel.getReview().getValue();
    }

    private void getUser() {
        review.setRecipientId(recipientId);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            reviewViewModel.getLoggedUser(user.getUid()).observe(getViewLifecycleOwner(), u -> review.setSenderId(u.getId()));
        }
    }

    private void managePicture() {
        pictureViewModel.getPicture().observe(getViewLifecycleOwner(), photo -> {
            if (photo.getPath() != null) {
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
            if (!isEmptyOrOversize(review.getTitle()) && !isEmptyOrOversize(review.getComment())) {
                showValidationDialog();
            } else {
                if (review.getTitle().isEmpty()) titleLayout.setError(getString(R.string.error_empty));
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
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, b) -> review.setNote((int) rating));
        review.setNote((int) ratingBar.getRating());

        titleView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence input, int i, int i1, int i2) {
                String title = input.toString().trim();

                review.setTitle(title);

                if (isEmptyOrOversize(title)) titleLayout.setError(getString(R.string.error_oversize));
                else titleLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

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
            PictureDialogFragment pictureDialogFragment = PictureDialogFragment.newInstance();
            pictureDialogFragment.show(fragmentManager, "photoFragment");
        });
    }

    private boolean isEmptyOrOversize(String input) {
        return input.isEmpty() || input.length() > 200;
    }

    // Todo() : Upload la/les photo sur le serveur de stockage des photos
    private void showValidationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getString(R.string.title_evaluation))
                .setMessage(getString(R.string.disclaimer_leave_appreciation))
                .setPositiveButton(getString(R.string.label_yes), (dialogInterface, i) -> createReview())
                .setNegativeButton(getString(R.string.label_cancel), (dialogInterface, i) -> {})
                .show();
    }

    private void createReview() {
        review.setPictureList(pictureList);

        String currentDate = DateUtils.formatDateToString(Calendar.getInstance().getTime());
        review.setCreatedAt(currentDate);
        review.setUpdatedAt(currentDate);

        reviewViewModel.createReview(review).observe(getViewLifecycleOwner(), review -> {
            // Todo () : Do something when call fail and when call succeed
            requireActivity().onBackPressed();
        });
    }
}