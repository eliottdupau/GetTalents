package com.eliottdup.gettalents.ui.skills;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eliottdup.gettalents.R;


public class CreateSkillsFragment extends Fragment {

    public CreateSkillsFragment() {}

    public static CreateSkillsFragment newInstance() {
        return  new CreateSkillsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_skills, container, false);
    }
}