package com.eliottdup.gettalents.ui.authentication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.MainActivity;

public class SignInFragment extends Fragment {

    private FragmentManager fragmentManager;

    private  ForgetPasswordFragment forgetPasswordFragment;

    private Button btnSignIn;
    private Button btnForgetPassWord;

    public SignInFragment() {
        // Required empty public constructor
    }

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

        this.fragmentManager = getParentFragmentManager();

        if(forgetPasswordFragment == null) forgetPasswordFragment = ForgetPasswordFragment.newInstance();

        this.btnSignIn = root.findViewById(R.id.btnConnexion);
        this.btnForgetPassWord = root.findViewById(R.id.btnForgetPassword);

        changeFragment(btnForgetPassWord,forgetPasswordFragment);
        changeFragment(btnSignIn,null);

        return root;
    }

    private void changeFragment(Button btn, Fragment fragment) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragment == null){
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                if(fragment == forgetPasswordFragment){
                    fragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, forgetPasswordFragment)
                            .commit();
                }
            }
        });
    }
}