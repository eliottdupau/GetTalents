package com.eliottdup.gettalents.ui.profile.consult;

import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.adapter.address.AddressAdapter;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.utils.DateUtils;
import com.eliottdup.gettalents.viewmodel.UserViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class MyProfileFragment extends Fragment {
    private MaterialToolbar toolbar;
    private ImageView profilePicture;
    private TextView pseudoView, birthdayView, mailView;
    private RecyclerView recyclerView;

    private UserViewModel viewModel;

    private AddressAdapter adapter;
    private List<Address> addresses;

    public OnButtonClickedListener callback;

    public interface OnButtonClickedListener {
        void onBackClicked();
        void onEditClicked();
    }

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

        toolbar = root.findViewById(R.id.topAppBar);
        profilePicture = root.findViewById(R.id.icon_profilePicture);
        pseudoView = root.findViewById(R.id.textView_pseudo);
        birthdayView = root.findViewById(R.id.textView_birthday);
        mailView = root.findViewById(R.id.textView_mail);
        recyclerView = root.findViewById(R.id.recyclerView_address);

        return root;
    }

    // Todo() : Vérifier que le userLogged est bien récupéré à chaque fois que l'on revient sur ce fragment
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        configureToolbar();
        configureRecyclerView();

        getUser();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.createCallbackToParentActivity();
    }

    private void createCallbackToParentActivity() {
        callback = (OnButtonClickedListener) getActivity();
    }

    private void configureToolbar() {
        toolbar.setTitle(getString(R.string.title_profile));
        toolbar.inflateMenu(R.menu.app_bar_consult_profile_menu);
        toolbar.setNavigationOnClickListener(view -> callback.onBackClicked());

        toolbar.setOnMenuItemClickListener(item -> {
            callback.onEditClicked();

            return false;
        });
    }

    private void configureRecyclerView() {
        addresses = new ArrayList<>();
        adapter = new AddressAdapter(addresses, R.layout.item_address);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getUser() {
        viewModel.getLoggedUser();
        viewModel.getUser().observe(getViewLifecycleOwner(), this::updateUI);
    }

    private void updateUI(User user) {
        Glide.with(this)
                .load(user.getProfilePicture().getUri())
                .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                .into(profilePicture);

        pseudoView.setText(user.getPseudo());
        mailView.setText(user.getMail());

        birthdayView.setText(DateUtils.formatDate(user.getBirthday()));

        addresses = user.getAddresses();
        adapter.updateData(addresses);
    }
}