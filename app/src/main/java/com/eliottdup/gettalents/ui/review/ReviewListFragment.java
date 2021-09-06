package com.eliottdup.gettalents.ui.review;

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

import com.bumptech.glide.Glide;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.adapter.review.ReviewAdapter;
import com.eliottdup.gettalents.model.Review;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.viewmodel.ReviewListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReviewListFragment extends Fragment {
    private RecyclerView recyclerView;

    private ReviewListViewModel viewModel;

    private ReviewAdapter adapter;
    private List<Review> reviewList;

    public ReviewListFragment() { }

    public static ReviewListFragment newInstance() {
        return new ReviewListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_review_list, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ReviewListViewModel.class);

        configureRecyclerView();
        getUser();
    }

    private void configureRecyclerView() {
        reviewList = new ArrayList<>();
        adapter = new ReviewAdapter(reviewList, Glide.with(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getUser() {
        viewModel.getLoggedUser();
        viewModel.user.observe(getViewLifecycleOwner(), this::getReceivedReviews);
    }

    private void getReceivedReviews(User user) {
        viewModel.getReceivedReviewsForUser(user.getId());
        viewModel.reviewList.observe(getViewLifecycleOwner(), this::updateUI);
    }

    private void updateUI(List<Review> reviews) {
        reviewList = reviews;
        adapter.updateData(reviewList);
    }
}