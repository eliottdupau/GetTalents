package com.eliottdup.gettalents.ui.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.viewmodel.UserViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddressActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private FloatingActionButton addAddressButton;

    private UserViewModel viewModel;

    private FragmentManager fragmentManager;
    private AddressesFragment addressesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        toolbar = findViewById(R.id.topAppBar);
        addAddressButton = findViewById(R.id.button_addAddress);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        fragmentManager = getSupportFragmentManager();

        configureToolbar();
        setupView();
        getUser();
    }

    private void configureToolbar() {
        toolbar.setTitle(getString(R.string.title_my_addresses));
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void setupView() {
        if (addressesFragment == null) addressesFragment = AddressesFragment.newInstance(true);

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, addressesFragment)
                .commit();

        addAddressButton.setOnClickListener(view -> {
            CreateAddressDialogFragment createAddressDialogFragment = CreateAddressDialogFragment.newInstance();
            createAddressDialogFragment.show(fragmentManager, "createAddressFragment");
        });
    }

    private void getUser() {
        viewModel.getLoggedUser();
    }
}