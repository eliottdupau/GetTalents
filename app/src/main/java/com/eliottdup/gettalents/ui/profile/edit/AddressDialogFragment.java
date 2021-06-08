package com.eliottdup.gettalents.ui.profile.edit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Address;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddressDialogFragment extends DialogFragment {
    private static final String KEY_ADDRESS = "address";

    private TextInputLayout addressLayout, zipCodeLayout, cityLayout, countryLayout;
    private TextInputEditText addressView, zipCodeView, cityView, countryView;
    private MaterialButton positiveButton, negativeButton;

    private Address address;
    private String addressStr, zipCodeStr, cityStr, countryStr;

    public AddressDialogFragment() {}

    public static AddressDialogFragment newInstance() {
        return new AddressDialogFragment();
    }

    public static AddressDialogFragment newInstance(Address address) {
        AddressDialogFragment addressDialogFragment = new AddressDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable(KEY_ADDRESS, address);
        addressDialogFragment.setArguments(args);

        return addressDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_address_dialog, container, false);

        addressLayout = root.findViewById(R.id.inputLayout_address);
        zipCodeLayout = root.findViewById(R.id.inputLayout_zipCode);
        cityLayout = root.findViewById(R.id.inputLayout_city);
        countryLayout = root.findViewById(R.id.inputLayout_country);
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

        if (getArguments() != null) {
            this.address = (Address) getArguments().getSerializable(KEY_ADDRESS);
        }

        initView();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    private void initView() {
        if (this.address != null) {
            addressView.setText(address.getAddress());
            zipCodeView.setText(address.getZipCode());
            cityView.setText(address.getCity());
            countryView.setText(address.getCountry());
        }

        addressStr = this.address != null ? address.getAddress() : "";
        zipCodeStr = this.address != null ? address.getZipCode() : "";
        cityStr = this.address != null ? address.getCity() : "";
        countryStr = this.address != null ? address.getCountry() : "";

        addTextChangedListener(addressView);
        addTextChangedListener(zipCodeView);
        addTextChangedListener(cityView);
        addTextChangedListener(countryView);

        String address = String.format("%s, %s %s - %s", addressView.getText(), zipCodeView.getText(), cityView.getText(), countryView.getText());
        positiveButton.setOnClickListener(view -> {
            if (isAddressCorrect()) {
                Toast.makeText(getContext(), address, Toast.LENGTH_SHORT).show();
                dismiss();
            } else {
                if (addressStr.isEmpty()) addressLayout.setError(getString(R.string.error_empty));
                if (zipCodeStr.isEmpty()) zipCodeLayout.setError(getString(R.string.error_empty));
                if (cityStr.isEmpty()) cityLayout.setError(getString(R.string.error_empty));
                if (countryStr.isEmpty()) countryLayout.setError(getString(R.string.error_empty));
            }
        });

        negativeButton.setOnClickListener(view -> dismiss());
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
            case R.id.editText_address:
                addressStr = input;
                if (addressLayout.getError() != null) addressLayout.setError(null);
                break;
            case R.id.editText_zipCode:
                zipCodeStr = input;
                if (zipCodeLayout.getError() != null) zipCodeLayout.setError(null);
                break;
            case R.id.editText_city:
                cityStr = input;
                if (cityLayout.getError() != null) cityLayout.setError(null);
                break;
            case R.id.editText_country:
                countryStr = input;
                if (countryLayout.getError() != null) countryLayout.setError(null);
                break;
        }
    }

    private boolean isAddressCorrect() {
        return addressStr.length() > 0 && zipCodeStr.length() > 0 &&
                cityStr.length() > 0 && countryStr.length() > 0;
    }
}