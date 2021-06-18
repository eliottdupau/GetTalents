package com.eliottdup.gettalents.ui.authentication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.MainActivity;

public class AuthenticationFragment extends Fragment {

    private FragmentManager fragmentManager;

    private  SignUpFragment signUpFragment;
    private  SignInFragment signInFragment;

    private Button btnSignIn;
    private Button btnSignUp;
    private Button btnVisitor;

    public AuthenticationFragment() {
        // Required empty public constructor
    }

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

        this.fragmentManager = getParentFragmentManager();

        this.btnSignIn = root.findViewById(R.id.btnSignIn);
        this.btnSignUp = root.findViewById(R.id.btnSignUp);
        this.btnVisitor = root.findViewById(R.id.btnVisitor);

        if(signInFragment == null) signInFragment = SignInFragment.newInstance();
        if(signUpFragment == null) signUpFragment = SignUpFragment.newInstance();

        changeFragment(btnSignIn,signInFragment);
        changeFragment(btnSignUp,signUpFragment);
        changeFragment(btnVisitor,null);

        return root;
    }

    private void changeFragment(Button btn, Fragment fragment) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fragment == null){
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                }
                if(fragment == signInFragment){
                    fragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, signInFragment)
                            .commit();
                }
                if(fragment == signUpFragment){
                    fragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, signUpFragment)
                            .commit();
                }
            }
        });
    }
}