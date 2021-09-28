package com.eliottdup.gettalents.ui.authentication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eliottdup.gettalents.R;
import com.google.android.material.button.MaterialButton;

public class AuthenticationFragment extends Fragment {
    private FragmentManager fragmentManager;

    public AuthenticationFragment() {}

    public static AuthenticationFragment newInstance() {
               return new AuthenticationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_authentication, container, false);

        fragmentManager = getParentFragmentManager();

        MaterialButton btnSignIn = root.findViewById(R.id.btnSignIn);
        MaterialButton btnSignUp = root.findViewById(R.id.btnSignUp);

        manageButtonClicked(btnSignIn);
        manageButtonClicked(btnSignUp);

        return root;
    }

    @SuppressLint("NonConstantResourceId")
    private void manageButtonClicked(Button btn) {
        btn.setOnClickListener(v -> {
            switch (v.getId()) {
                case R.id.btnSignIn:
                    fragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, SignInFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    break;
                case R.id.btnSignUp:
                    fragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, SignUpFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    break;
                default:
                    break;
            }
        });
    }
}