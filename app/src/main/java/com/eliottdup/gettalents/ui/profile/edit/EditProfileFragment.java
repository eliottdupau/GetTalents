package com.eliottdup.gettalents.ui.profile.edit;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.ui.profile.consult.AddressAdapter;
import com.eliottdup.gettalents.utils.DateUtils;
import com.eliottdup.gettalents.utils.ItemClickSupport;
import com.eliottdup.gettalents.viewmodel.UserViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class EditProfileFragment extends Fragment {
    private MaterialToolbar toolbar;
    private ImageView profilePicture;
    private MaterialCardView profilePictureCard, pseudoCard, birthdayCard;
    private TextView pseudoView, birthdayView, mailView;
    private MaterialButton addressButton;
    private RecyclerView recyclerView;

    private UserViewModel viewModel;

    private FragmentManager fragmentManager;

    private User user;

    private AddressAdapter adapter;
    private List<Address> addresses;

    public OnButtonClickedListener callback;

    public interface OnButtonClickedListener {
        void onBackButtonClicked();
        void onSaveChangesButtonClicked();
    }

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

        toolbar = root.findViewById(R.id.topAppBar);
        profilePictureCard = root.findViewById(R.id.container_profilePicture);
        profilePicture = root.findViewById(R.id.icon_profilePicture);
        pseudoCard = root.findViewById(R.id.container_pseudo);
        birthdayCard = root.findViewById(R.id.container_birthday);
        pseudoView = root.findViewById(R.id.textView_pseudo);
        birthdayView = root.findViewById(R.id.textView_birthday);
        mailView = root.findViewById(R.id.textView_mail);
        addressButton = root.findViewById(R.id.button_addAddress);
        recyclerView = root.findViewById(R.id.recyclerView_address);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        fragmentManager = getParentFragmentManager();

        configureToolbar();
        configureRecyclerView();

        getUser();
        setupView();
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
        toolbar.setTitle(getString(R.string.title_edit_profile));
        toolbar.inflateMenu(R.menu.app_bar_edit_profile_menu);
        toolbar.setNavigationOnClickListener(view -> callback.onBackButtonClicked());

        toolbar.setOnMenuItemClickListener(item -> {
            callback.onSaveChangesButtonClicked();

            return false;
        });
    }

    private void configureRecyclerView() {
        addresses = new ArrayList<>();
        adapter = new AddressAdapter(addresses, R.layout.item_edit_address);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemClickSupport.addTo(recyclerView, R.layout.item_edit_address)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Address address = adapter.getAddress(position);

                    UpdateAddressDialogFragment updateAddressDialogFragment = UpdateAddressDialogFragment.newInstance(address.getId());
                    updateAddressDialogFragment.show(fragmentManager, "updateAddressFragment");
                });
    }

    private void getUser() {
        user = viewModel.getUser().getValue();
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            this.user = user;
            updateUI(this.user);
        });
    }

    private void setupView() {
        updateUI(user);

        profilePictureCard.setOnClickListener(view -> {
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

        addressButton.setOnClickListener(view -> {
            CreateAddressDialogFragment createAddressDialogFragment = CreateAddressDialogFragment.newInstance();
            createAddressDialogFragment.show(fragmentManager, "createAddressFragment");
        });
    }

    private void updateUI(User user) {
        Glide.with(this)
                .load(user.getUrlProfilePicture())
                .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                .into(profilePicture);

        pseudoView.setText(user.getPseudo());
        mailView.setText(user.getMail());

        birthdayView.setText(DateUtils.formatDate(user.getBirthday()));

        addresses = user.getAddresses();
        adapter.updateData(addresses);
    }
}