package com.eliottdup.gettalents.ui.address;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.viewmodel.AddressViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class CreateAddressDialogFragment extends DialogFragment {
    private TextInputLayout numerousLayout, addressLayout, zipCodeLayout, cityLayout, countryLayout;
    private TextInputEditText numerousView, addressView, zipCodeView, cityView, countryView;
    private MaterialButton positiveButton, negativeButton;

    private AddressViewModel viewModel;

    private Address address;
    private User user;

    public CreateAddressDialogFragment() {}

    public static CreateAddressDialogFragment newInstance() {
        return new CreateAddressDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_address_dialog, container, false);

        numerousLayout = root.findViewById(R.id.inputLayout_numerous);
        addressLayout = root.findViewById(R.id.inputLayout_address);
        zipCodeLayout = root.findViewById(R.id.inputLayout_zipCode);
        cityLayout = root.findViewById(R.id.inputLayout_city);
        countryLayout = root.findViewById(R.id.inputLayout_country);
        numerousView = root.findViewById(R.id.editText_numerous);
        addressView = root.findViewById(R.id.editText_address);
        zipCodeView = root.findViewById(R.id.editText_zipCode);
        cityView = root.findViewById(R.id.editText_city);
        countryView = root.findViewById(R.id.editText_country);
        positiveButton = root.findViewById(R.id.button_add);
        negativeButton = root.findViewById(R.id.button_cancel);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AddressViewModel.class);

        address = new Address();

        setupView();
        getUser();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    private void setupView() {
        addTextChangedListener(numerousView);
        addTextChangedListener(addressView);
        addTextChangedListener(zipCodeView);
        addTextChangedListener(cityView);
        addTextChangedListener(countryView);

        positiveButton.setText(getString(R.string.label_add));
        positiveButton.setOnClickListener(view -> {
            if (isAddressCorrect()) {
                viewModel.createAddress(address).observe(getViewLifecycleOwner(), address -> {
                    viewModel.addAddressInList(address);

                    dismiss();
                });
            } else {
                if (address.getNumerous().isEmpty()) numerousLayout.setError(getString(R.string.error_empty));
                if (address.getStreet().isEmpty()) addressLayout.setError(getString(R.string.error_empty));
                if (address.getPostalCode().isEmpty()) zipCodeLayout.setError(getString(R.string.error_empty));
                if (address.getCity().isEmpty()) cityLayout.setError(getString(R.string.error_empty));
                if (address.getCountry().isEmpty()) countryLayout.setError(getString(R.string.error_empty));
            }
        });

        negativeButton.setOnClickListener(view -> dismiss());
    }

    private void getUser() {
        viewModel.user.observe(getViewLifecycleOwner(), user -> {
            this.user = user;
            address.setUserId(this.user.getId());
        });
    }

    private void addTextChangedListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence input, int i, int i1, int i2) {
                manageTextListener(editText, input.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void manageTextListener(EditText editText, String input) {
        switch (editText.getId()) {
            case R.id.editText_numerous:
                address.setNumerous(input);
                if (numerousLayout.getError() != null) numerousLayout.setError(null);
                break;
            case R.id.editText_address:
                address.setStreet(input);
                if (addressLayout.getError() != null) addressLayout.setError(null);
                break;
            case R.id.editText_zipCode:
                address.setPostalCode(input);
                if (zipCodeLayout.getError() != null) zipCodeLayout.setError(null);
                break;
            case R.id.editText_city:
                address.setCity(input);
                if (cityLayout.getError() != null) cityLayout.setError(null);
                break;
            case R.id.editText_country:
                address.setCountry(input);
                if (countryLayout.getError() != null) countryLayout.setError(null);
                break;
        }
    }

    private boolean isAddressCorrect() {
        return address.getNumerous().length() > 0 && address.getStreet().length() > 0
                && address.getPostalCode().length() > 0 && address.getCity().length() > 0
                && address.getCountry().length() > 0;
    }
}