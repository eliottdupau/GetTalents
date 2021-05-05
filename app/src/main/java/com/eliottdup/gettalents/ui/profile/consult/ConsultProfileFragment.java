package com.eliottdup.gettalents.ui.profile.consult;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ConsultProfileFragment extends Fragment {
    private ImageView profilePicture;
    private TextView pseudoView, nameView, birthdayView, mailView;
    private RecyclerView recyclerView;

    private User user;

    private AddressAdapter adapter;
    private List<Address> addresses;

    public ConsultProfileFragment() { }

    public static ConsultProfileFragment newInstance() {
        return new ConsultProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_consult_profile, container, false);

        profilePicture = root.findViewById(R.id.icon_profilePicture);
        pseudoView = root.findViewById(R.id.textView_pseudo);
        nameView = root.findViewById(R.id.textView_fullName);
        birthdayView = root.findViewById(R.id.textView_birthday);
        mailView = root.findViewById(R.id.textView_mail);
        recyclerView = root.findViewById(R.id.recyclerView_address);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureRecyclerView();

        getUser();
        setupView();
    }

    private void configureRecyclerView() {
        addresses = new ArrayList<>();
        adapter = new AddressAdapter(addresses);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getUser() {
        user = new User(UUID.randomUUID().toString());
        user.setPseudo("Lataupedu59");
        user.setName("Lataupe");
        user.setFirstName("René");
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

    private void setupView() {
        Glide.with(this)
                .load(user.getUrlProfilePicture())
                .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                .into(profilePicture);

        pseudoView.setText(user.getPseudo());
        nameView.setText(String.format("%s %s", user.getFirstName(), user.getName()));
        mailView.setText(user.getMail());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
        birthdayView.setText(dateFormat.format(user.getBirthday()));

        adapter.notifyDataSetChanged();
    }
}