package com.eliottdup.gettalents.ui.authentication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.MainActivity;
import com.eliottdup.gettalents.ui.profile.edit.CreateProfilFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpFragment extends Fragment {

    private FragmentManager fragmentManager;
    private CreateProfilFragment createProfilFragment;

    private Button btnSignUp;

    private TextInputLayout layoutEmail;
    private TextInputLayout layoutPassword;
    private TextInputLayout layoutConfirmPassword;

    private String email = "";
    private String password = "";
    private String confirmPassword = "";

    private MaterialToolbar toolbar;

    public OnButtonClickedListener callback;

    public interface OnButtonClickedListener {
        void onBackButtonClicked();
    }

    public SignUpFragment() {}

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_sign_up, container, false);

        this.fragmentManager = getParentFragmentManager();

        toolbar = root.findViewById(R.id.topAppBar);

        this.btnSignUp = root.findViewById(R.id.btnSignUp);

        this.layoutEmail = root.findViewById(R.id.layoutEmail);
        this.layoutPassword = root.findViewById(R.id.layoutPassword);
        this.layoutConfirmPassword = root.findViewById(R.id.layoutConfirmPassword);

        TextInputEditText inputEmail = root.findViewById(R.id.inputEmail);
        TextInputEditText inputPassword = root.findViewById(R.id.inputPassword);
        TextInputEditText inputConfirmPassword = root.findViewById(R.id.inputConfirmPassword);

        if(createProfilFragment == null) createProfilFragment = createProfilFragment.newInstance();

        configureToolbar();

        baseTextListener(inputEmail);
        baseTextListener(inputPassword);
        baseTextListener(inputConfirmPassword);

        signUp();

        return root;
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
        toolbar.setTitle(getString(R.string.sign_up));
        toolbar.setNavigationOnClickListener(view -> callback.onBackButtonClicked());
    }

    private void signUp(){
        btnSignUp.setOnClickListener(v -> {
//            if(checkForm()){
////                Intent intent = new Intent(getContext(), MainActivity.class);
////                startActivity(intent);
////                requireActivity().finish();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.mainContainer, createProfilFragment)
//                        .addToBackStack(null)
//                        .commit();
//            }
//            else{
//                if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
//                    layoutEmail.setError(getString(R.string.error_email_format));
//                if(password.length() <= 7)
//                    layoutPassword.setError(getString(R.string.error_password_length));
//                if(!confirmPassword.equals(password))
//                    layoutConfirmPassword.setError(getString(R.string.error_password_correspond));
//            }
            fragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, createProfilFragment)
                    .addToBackStack(null)
                    .commit();

        });
    }

    private boolean checkForm(){
            return email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
                    && password.length() > 7
                    && confirmPassword.equals(password) ;
    }

    private void baseTextListener(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resetErrorLayout(editText,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void resetErrorLayout(EditText editText , String input){
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
            case R.id.inputConfirmPassword:
                confirmPassword = input;
                if(layoutConfirmPassword.getError() != null)
                    layoutConfirmPassword.setError(null);
                break;
            default:
                break;
        }
    }
}