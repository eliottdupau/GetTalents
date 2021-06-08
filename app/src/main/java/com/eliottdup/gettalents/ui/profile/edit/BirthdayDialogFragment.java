package com.eliottdup.gettalents.ui.profile.edit;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.utils.DateUtils;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.UUID;

public class BirthdayDialogFragment extends DialogFragment {
    private DatePicker datePicker;
    private MaterialButton positiveButton, negativeButton;

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

        getUser();
        initView();

        return root;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    private void getUser() {
        user = new User(UUID.randomUUID().toString());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 4);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.YEAR, 1996);
        user.setBirthday(calendar.getTime());
    }

    private void initView() {
        positiveButton.setOnClickListener(view -> {
            Toast.makeText(getContext(),
                    DateUtils.formatDate(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth()),
                    Toast.LENGTH_SHORT).show();
            dismiss();
        });

        negativeButton.setOnClickListener(view -> dismiss());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(user.getBirthday());
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }
}