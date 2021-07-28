package com.eliottdup.gettalents.ui.profile.edit;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.utils.DateUtils;
import com.eliottdup.gettalents.viewmodel.UserViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

public class BirthdayDialogFragment extends DialogFragment {
    private DatePicker datePicker;
    private MaterialButton positiveButton, negativeButton;

    private UserViewModel viewModel;

    private User user;

    public BirthdayDialogFragment() {}

    public static BirthdayDialogFragment newInstance() {
        return new BirthdayDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_birthday_dialog, container, false);

        datePicker = root.findViewById(R.id.birthdayPicker);
        positiveButton = root.findViewById(R.id.button_save);
        negativeButton = root.findViewById(R.id.button_cancel);

        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        setupView();

        getUser();

        return root;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    private void getUser() {
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            this.user = user;
            updateUI(user);
        });
    }

    private void setupView() {
        datePicker.setMaxDate(Calendar.getInstance().getTime().getTime());

        positiveButton.setOnClickListener(view -> {
            user.setBirthday(DateUtils.formatDate(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth()));
            viewModel.setUser(user);
            dismiss();
        });

        negativeButton.setOnClickListener(view -> dismiss());
    }

    private void updateUI(User user) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(user.getBirthday());
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }
}