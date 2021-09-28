package com.eliottdup.gettalents.ui.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.ui.MainActivity;
import com.eliottdup.gettalents.ui.profile.create.CreateProfileActivity;
import com.eliottdup.gettalents.ui.profile.create.CreateProfileFragment;

public class AuthenticationActivity extends AppCompatActivity implements SignUpFragment.OnButtonClickedListener, SignInFragment.OnButtonClickedListener, ForgetPasswordFragment.OnButtonClickedListener {

    private FragmentManager fragmentManager ;
    private AuthenticationFragment authenticationFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        fragmentManager = getSupportFragmentManager();

        if (authenticationFragment == null) authenticationFragment = AuthenticationFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, authenticationFragment)
                .commit();
    }

    @Override
    public void onBackButtonClicked() {
        onBackPressed();
    }
}