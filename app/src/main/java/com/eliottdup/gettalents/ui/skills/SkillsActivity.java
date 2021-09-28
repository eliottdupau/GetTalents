package com.eliottdup.gettalents.ui.skills;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.eliottdup.gettalents.R;

public class SkillsActivity extends AppCompatActivity {

    private CreateSkillsFragment createSkillsFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(createSkillsFragment == null) createSkillsFragment = CreateSkillsFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, createSkillsFragment)
                .commit();
    }
}