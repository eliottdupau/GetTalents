package com.eliottdup.gettalents.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Conversation;
import com.eliottdup.gettalents.utils.KeyUtils;

public class MessageActivity extends AppCompatActivity {

    private Conversation conversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        conversation = (Conversation) getIntent().getSerializableExtra(KeyUtils.KEY_CONVERSATION);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer,MessageFragment.newInstance(conversation))
                .commit();

    }
}