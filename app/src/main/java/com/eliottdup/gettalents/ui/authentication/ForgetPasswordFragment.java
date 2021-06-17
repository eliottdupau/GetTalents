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

public class ForgetPasswordFragment extends Fragment {

    private FragmentManager fragmentManager;
    private Button btnSend;

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

        this.btnSend = root.findViewById(R.id.btnSend);

        send();

        return root;
    }

    private void send(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}