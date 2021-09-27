package com.eliottdup.gettalents.ui.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Conversation;
import com.eliottdup.gettalents.utils.KeyUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {

    private Conversation conversation;

    public MessageFragment() {
        // Required empty public constructor
    }

    public static MessageFragment newInstance(Conversation conversation) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putSerializable(KeyUtils.KEY_CONVERSATION, conversation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.conversation = (Conversation) getArguments().getSerializable(KeyUtils.KEY_CONVERSATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }
}