package com.eliottdup.gettalents.ui.profile.edit;

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
import android.widget.Toast;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.UUID;

public class PseudoDialogFragment extends DialogFragment {
    private TextInputLayout pseudoLayout;
    private TextInputEditText pseudoView;
    private MaterialButton positiveButton, negativeButton;

    private User user;
    private String pseudo;

    public PseudoDialogFragment() { }

    public static PseudoDialogFragment newInstance() {
        return new PseudoDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dialog_pseudo, container, false);

        pseudoLayout = root.findViewById(R.id.inputLayout_pseudo);
        pseudoView = root.findViewById(R.id.editText_pseudo);
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
        user.setPseudo("Lataupedu59");
    }

    private void initView() {
        pseudoView.setText(user.getPseudo());
        pseudo = user.getPseudo();

        pseudoView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence input, int i, int i1, int i2) {
                pseudo = input.toString().trim();

                if (pseudoLayout.getError() != null) pseudoLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        positiveButton.setOnClickListener(view -> {
            if (pseudo.length() > 0) {
                Toast.makeText(getContext(), pseudoView.getText(), Toast.LENGTH_SHORT).show();
                dismiss();
            } else {
                pseudoLayout.setError(getString(R.string.error_empty));
            }
        });

        negativeButton.setOnClickListener(view -> dismiss());
    }
}