package com.eliottdup.gettalents.ui.relation;

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

import com.bumptech.glide.Glide;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.adapter.relation.RelationAdapter;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.ui.address.UpdateAddressDialogFragment;
import com.eliottdup.gettalents.ui.profile.consult.other.UserProfileActivity;
import com.eliottdup.gettalents.utils.ItemClickSupport;
import com.eliottdup.gettalents.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

// Todo() : Rajouter un message + image quand le user n'a pas de relation
public class RelationFragment extends Fragment {
    private RecyclerView recyclerView;

    private UserViewModel viewModel;

    private RelationAdapter adapter;
    private List<User> relationList;

    public RelationFragment() {}

    public static RelationFragment newInstance() {
        return new RelationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_relation, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        configureRecyclerView();
        getUser();
    }

    private void configureRecyclerView() {
        relationList = new ArrayList<>();
        adapter = new RelationAdapter(relationList, Glide.with(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemClickSupport.addTo(recyclerView, R.layout.item_relation)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    User user = adapter.getUser(position);

                    Intent intent = new Intent(getContext(), UserProfileActivity.class);
                    intent.putExtra(UserProfileActivity.KEY_USER_ID, user.getId());
                    startActivity(intent);
                });
    }

    private void getUser() {
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            /*relationList = user.getRelationList();
            adapter.updateData(relationList);*/
        });
    }
}