package com.eliottdup.gettalents.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.eliottdup.gettalents.data.repository.ConversationRepository;
import com.eliottdup.gettalents.model.Conversation;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {

    private final ConversationRepository conversationRepository;

    private MutableLiveData<List<Conversation>> conversations ;

    public ChatViewModel(@NonNull Application application) {
        super(application);

        conversationRepository = new ConversationRepository();
    }

    public void GetConversationByUserId(int id) {
        this.conversations = conversationRepository.GetConversationByUserId(id);
    }
}
