package com.eliottdup.gettalents.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Conversation;

public class ChatActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private ConversationFragment conversationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        fragmentManager = getSupportFragmentManager();

        if (conversationFragment == null) conversationFragment = ConversationFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, conversationFragment)
                .commit();
    }
}