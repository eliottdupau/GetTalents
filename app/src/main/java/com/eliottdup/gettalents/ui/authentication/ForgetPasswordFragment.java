package com.eliottdup.gettalents.ui.authentication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.utils.ApiUtils;
import com.eliottdup.gettalents.utils.AuthenticationUtils;
import com.eliottdup.gettalents.utils.ErrorUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordFragment extends Fragment {
    private CoordinatorLayout mainContainer;
    private LinearLayout loadingContainer;
    private Button btnSend;
    private TextInputLayout emailLayout;
    private TextInputEditText emailView;
    private MaterialToolbar toolbar;

    private String email = "";

    public OnButtonClickedListener callback;

    public interface OnButtonClickedListener {
        void onBackButtonClicked();
    }

    public ForgetPasswordFragment() { }

    public static ForgetPasswordFragment newInstance() {
        return new ForgetPasswordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_forget_password, container, false);

        mainContainer = root.findViewById(R.id.mainContainer);
        loadingContainer = root.findViewById(R.id.loadingContainer);
        toolbar = root.findViewById(R.id.topAppBar);
        btnSend = root.findViewById(R.id.btnSend);
        emailLayout = root.findViewById(R.id.inputLayout_email);
        emailView = root.findViewById(R.id.editText_email);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureToolbar();
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
        toolbar.setTitle(getString(R.string.forgetPassword));
        toolbar.setNavigationOnClickListener(view -> callback.onBackButtonClicked());
    }

    private void setupView() {
        btnSend.setOnClickListener(view -> {
            if (AuthenticationUtils.checkEmail(email)) sendEmail(email);
            else emailLayout.setError(getString(R.string.error_email_format));
        });

        emailView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence input, int i, int i1, int i2) {
                email = input.toString().trim();

                if (emailLayout.getError() != null) emailLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    private void sendEmail(String email) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        ApiUtils.showLoading(loadingContainer, true);

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    ApiUtils.showLoading(loadingContainer, false);
                    if (task.isSuccessful()) callback.onBackButtonClicked();
                    else {
                        ErrorUtils.displayLongError(mainContainer, "Email incorrect, if the problem persist try again later");
                    }
                });
    }
}