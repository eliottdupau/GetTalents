package com.eliottdup.gettalents.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.eliottdup.gettalents.data.repository.ConversationRepository;
import com.eliottdup.gettalents.data.repository.UserRepository;
import com.eliottdup.gettalents.model.Conversation;
import com.eliottdup.gettalents.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends AndroidViewModel {


    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private MutableLiveData<List<User>> users;

    public static MutableLiveData<User> user;
    public MutableLiveData<List<Conversation>> conversations ;

    public ChatViewModel(@NonNull Application application) {
        super(application);

        conversationRepository = new ConversationRepository();
        userRepository = new UserRepository();
    }

    public MutableLiveData<List<Conversation>> getConversationByUserId(int id) {
        return conversationRepository.GetConversationByUserId(id);
    }

    public void getLoggedUser(String id) {
        this.user = userRepository.getUserById(id);
    }

    public MutableLiveData<User> getUserById(String id) {
       return this.userRepository.getUserById(id);
    }



}
