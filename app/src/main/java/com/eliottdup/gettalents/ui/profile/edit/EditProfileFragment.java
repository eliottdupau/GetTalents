package com.eliottdup.gettalents.ui.profile.edit;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.eliottdup.gettalents.utils.ItemClickSupport;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class EditProfileFragment extends Fragment {
    private MaterialToolbar toolbar;
    private ImageView profilePicture;
    private MaterialCardView profilePictureCard, pseudoCard, birthdayCard;
    private TextView pseudoView, birthdayView, mailView;
    private MaterialButton addressButton;
    private RecyclerView recyclerView;

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

        configureToolbar();
        configureRecyclerView();
        getUser();
        initView();

        fragmentManager = getParentFragmentManager();
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

                    AddressDialogFragment addressDialogFragment = AddressDialogFragment.newInstance(address);
                    addressDialogFragment.show(fragmentManager, "addressFragment");
                });
    }

    private void getUser() {
        user = new User(UUID.randomUUID().toString());
        user.setPseudo("Lataupedu59");
        user.setMail("rene.lataupe@yahoo.fr");
        user.setUrlProfilePicture("https://torange.biz/photofxnew/76/HD/lion-profile-picture-76801.jpg");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 4);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.YEAR, 1996);
        user.setBirthday(calendar.getTime());

        Address address = new Address(UUID.randomUUID().toString());
        address.setAddress("34 rue des peupliers");
        address.setZipCode("59360");
        address.setCity("Seclin");
        address.setCountry("France");

        Address address2 = new Address(UUID.randomUUID().toString());
        address2.setAddress("122 rue des grands arbres qui poussent vite");
        address2.setZipCode("60000");
        address2.setCity("Saint Quentin");
        address2.setCountry("France");

        Address address3 = new Address(UUID.randomUUID().toString());
        address3.setAddress("26 avenue des puits");
        address3.setZipCode("02387");
        address3.setCity("Compiègne");
        address3.setCountry("France");

        addresses.add(address);
        addresses.add(address2);
        addresses.add(address3);
    }

    private void initView() {
        Glide.with(this)
                .load(user.getUrlProfilePicture())
                .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                .into(profilePicture);

        pseudoView.setText(user.getPseudo());
        mailView.setText(user.getMail());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
        birthdayView.setText(dateFormat.format(user.getBirthday()));

        adapter.notifyDataSetChanged();

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
            AddressDialogFragment addressDialogFragment = AddressDialogFragment.newInstance();
            addressDialogFragment.show(fragmentManager, "addressFragment");
        });
    }
}