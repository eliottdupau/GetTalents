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

public class ForgetPasswordFragment extends Fragment {

    private FragmentManager fragmentManager;
    private Button btnSend;
    private MaterialToolbar toolbar;
    public OnButtonClickedListener callback;

    public interface OnButtonClickedListener {
        void onBackButtonClicked();
    }

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    public static ForgetPasswordFragment newInstance() {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        return fragment;
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

        this.fragmentManager = getParentFragmentManager();
        this.toolbar = root.findViewById(R.id.topAppBar);
        this.btnSend = root.findViewById(R.id.btnSend);

        configureToolbar();
        send();

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
        toolbar.setTitle(getString(R.string.forgetPassword));
        toolbar.setNavigationOnClickListener(view -> callback.onBackButtonClicked());
    }

    private void send(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
    }
}