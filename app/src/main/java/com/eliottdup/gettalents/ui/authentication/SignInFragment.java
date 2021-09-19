package com.eliottdup.gettalents.ui.authentication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.MainActivity;
import com.eliottdup.gettalents.utils.ApiUtils;
import com.eliottdup.gettalents.utils.AuthenticationUtils;
import com.eliottdup.gettalents.viewmodel.AuthenticationViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignInFragment extends Fragment {
    private CoordinatorLayout coordinatorLayout;
    private LinearLayout loadingContainer;
    private ForgetPasswordFragment forgetPasswordFragment;
    private Button btnSignIn, btnForgetPassWord;
    private MaterialToolbar toolbar;
    private TextInputLayout layoutEmail, layoutPassword;
    private TextInputEditText inputEmail, inputPassword;

    private AuthenticationViewModel viewModel;

    private FragmentManager fragmentManager;

    private String email = "", password = "";

    public OnButtonClickedListener callback;

    public interface OnButtonClickedListener {
        void onBackButtonClicked();
    }

    public SignInFragment() { }

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_sign_in, container, false);

        coordinatorLayout = root.findViewById(R.id.mainContainer);
        loadingContainer = root.findViewById(R.id.loadingContainer);
        toolbar = root.findViewById(R.id.topAppBar);
        btnSignIn = root.findViewById(R.id.btnConnexion);
        btnForgetPassWord = root.findViewById(R.id.btnForgetPassword);
        layoutEmail = root.findViewById(R.id.layoutEmail);
        layoutPassword = root.findViewById(R.id.layoutPassword);
        inputEmail = root.findViewById(R.id.inputEmail);
        inputPassword = root.findViewById(R.id.inputPassword);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);

        fragmentManager = getParentFragmentManager();

        configureToolbar();

        baseTextListener(inputEmail);
        baseTextListener(inputPassword);

        changeFragment(btnForgetPassWord);
        changeFragment(btnSignIn);
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
        toolbar.setTitle(getString(R.string.connection));
        toolbar.setNavigationOnClickListener(view -> callback.onBackButtonClicked());
    }

    @SuppressLint("NonConstantResourceId")
    private void changeFragment(Button btn) {
        btn.setOnClickListener(v -> {
            switch (btn.getId()) {
                case R.id.btnConnexion:
                    if (AuthenticationUtils.checkSignInForm(email, password)) {
                        signIn(email, password);
                    } else {
                        if (!AuthenticationUtils.checkEmail(email)) layoutEmail.setError(getString(R.string.error_email_format));
                        if (!AuthenticationUtils.checkPassword(password)) layoutPassword.setError(getString(R.string.error_password_length));
                    }
                    break;
                case R.id.btnForgetPassword:
                    if(forgetPasswordFragment == null) forgetPasswordFragment = ForgetPasswordFragment.newInstance();

                    fragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, forgetPasswordFragment)
                            .addToBackStack(null)
                            .commit();
                    break;
                default:
                    break;
            }
        });
    }

    private void signIn(String email, String password) {
        viewModel.signInWithFirebase(email, password);
        ApiUtils.showLoading(loadingContainer, true);
        viewModel.userInFirebase.observe(getViewLifecycleOwner(), user -> {
            ApiUtils.showLoading(loadingContainer, false);
            if (user != null) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            } else {
                showSignInError();
            }
        });
    }

    private void showSignInError() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Id incorrects", Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorError));

        snackbar.show();
    }

    private void baseTextListener(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                manageInput(editText,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void manageInput(EditText editText , String input){
        switch (editText.getId()){
            case R.id.inputEmail :
                email = input;
                if(layoutEmail.getError() != null)
                    layoutEmail.setError(null);
                break;
            case R.id.inputPassword:
                password = input;
                if(layoutPassword.getError() != null)
                    layoutPassword.setError(null);
                break;
            default:
                break;
        }
    }
}