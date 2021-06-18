package com.eliottdup.gettalents.ui.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.MainActivity;
import com.eliottdup.gettalents.ui.profile.edit.EditProfileFragment;
import com.google.android.material.appbar.MaterialToolbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    private FragmentManager fragmentManager;
    private Button btnSignUp;
    private MaterialToolbar toolbar;
    public OnButtonClickedListener callback;

    public interface OnButtonClickedListener {
        void onBackButtonClicked();
    }

    public SignUpFragment() {
        // Required empty public constructor
    }

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

        toolbar = root.findViewById(R.id.topAppBar);
        this.fragmentManager = getParentFragmentManager();
        this.btnSignUp = root.findViewById(R.id.btnSignUp);

        configureToolbar();
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
        toolbar.setTitle(getString(R.string.SignUp));
        toolbar.setNavigationOnClickListener(view -> callback.onBackButtonClicked());
    }

    private void signUp(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
    }
}