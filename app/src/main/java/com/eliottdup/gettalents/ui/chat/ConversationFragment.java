package com.eliottdup.gettalents.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.adapter.Conversation.ConversationAdapteur;
import com.eliottdup.gettalents.model.Conversation;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.utils.ItemClickSupport;
import com.eliottdup.gettalents.utils.KeyUtils;
import com.eliottdup.gettalents.viewmodel.ChatViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConversationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConversationFragment extends Fragment {

    private RecyclerView conversationRecyclerView ;

    private ChatViewModel chatViewModel;

    private ConversationAdapteur conversationAdapteur;

    private List<Conversation> conversations;

    private FirebaseUser firebaseUser;
    private User user;

    public ConversationFragment() {
    }

    public static ConversationFragment newInstance() {
        return new ConversationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_conversation, container, false);
        conversationRecyclerView = root.findViewById(R.id.recyclerView_conversation);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        chatViewModel = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);

        configureConversationRecyclerView();

        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        getUser();
    }
    public void configureConversationRecyclerView(){
        conversations = new ArrayList<>();
        conversationAdapteur = new ConversationAdapteur(this.conversations,getContext());
        conversationRecyclerView.setAdapter(conversationAdapteur);
        conversationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        ItemClickSupport.addTo(conversationRecyclerView, R.layout.item_conversation).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Conversation conversation = conversationAdapteur.getConversation((position));
                Intent intent = new Intent(getContext(),MessageActivity.class);
                intent.putExtra(KeyUtils.KEY_CONVERSATION, conversation);
                startActivity(intent);
            }
        });
    }

    private void getUser() {
         firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            chatViewModel.getLoggedUser(firebaseUser.getUid());
            chatViewModel.user.observe(getViewLifecycleOwner(), user1 -> {
                this.user = user1;
                getConversations();
            });
        }
    }

    private void getConversations(){
//        chatViewModel.getConversationByUserId(user.getId());
        chatViewModel.getConversationByUserId(user.getId()).observe(getViewLifecycleOwner(), conversations -> {
            this.conversations = conversations;

            Log.d("Conversation", "" + this.conversations.size());
            conversationAdapteur.updateData(this.conversations);


        });

    }
}