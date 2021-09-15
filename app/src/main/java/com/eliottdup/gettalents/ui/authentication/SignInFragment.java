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
import com.google.android.material.appbar.MaterialToolbar;

public class SignInFragment extends Fragment {

    private FragmentManager fragmentManager;
    private  ForgetPasswordFragment forgetPasswordFragment;
    private Button btnSignIn;
    private Button btnForgetPassWord;
    private MaterialToolbar toolbar;
    public OnButtonClickedListener callback;

    public interface OnButtonClickedListener {
        void onBackButtonClicked();
    }

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

        toolbar = root.findViewById(R.id.topAppBar);
        this.fragmentManager = getParentFragmentManager();
        if(forgetPasswordFragment == null) forgetPasswordFragment = ForgetPasswordFragment.newInstance();
        this.btnSignIn = root.findViewById(R.id.btnConnexion);
        this.btnForgetPassWord = root.findViewById(R.id.btnForgetPassword);

        configureToolbar();
        changeFragment(btnForgetPassWord,forgetPasswordFragment);
        changeFragment(btnSignIn,null);

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
        toolbar.setTitle(getString(R.string.connection));
        toolbar.setNavigationOnClickListener(view -> callback.onBackButtonClicked());
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
                if(fragment == forgetPasswordFragment){
                    fragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, forgetPasswordFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }
}