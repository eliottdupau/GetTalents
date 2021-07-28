package com.eliottdup.gettalents.ui.profile.consult.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.adapter.profile.MyProfilePagerAdapter;
import com.eliottdup.gettalents.ui.profile.edit.EditProfileActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class MyProfileActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private MyProfileFragment myProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        toolbar = findViewById(R.id.topAppBar);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        FragmentManager fragmentManager = getSupportFragmentManager();

        configureToolbar();
        configureViewPager();

        if (myProfileFragment == null) myProfileFragment = MyProfileFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, myProfileFragment)
                .commit();
    }

    private void configureToolbar() {
        toolbar.setTitle(getString(R.string.title_profile));
        toolbar.inflateMenu(R.menu.app_bar_consult_profile_menu);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        toolbar.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);

            return false;
        });
    }

    private void configureViewPager() {
        MyProfilePagerAdapter pagerAdapter = new MyProfilePagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                this,
                false);

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}