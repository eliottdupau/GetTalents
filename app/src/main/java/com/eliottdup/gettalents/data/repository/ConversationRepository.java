package com.eliottdup.gettalents.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.eliottdup.gettalents.data.RetrofitInstance;
import com.eliottdup.gettalents.data.service.ConversationService;
import com.eliottdup.gettalents.model.Conversation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationRepository {

    public MutableLiveData<List<Conversation>> GetConversationByUserId(int userid){
        MutableLiveData<List<Conversation>> conversationsMutableLiveData = new MutableLiveData<>();

        ConversationService conversationService = RetrofitInstance.getInstance().create(ConversationService.class);
        Call<List<Conversation>> call = conversationService.GetConversationByUserId(userid) ;
        call.enqueue(new Callback<List<Conversation>>() {
            @Override
            public void onResponse(@NonNull Call<List<Conversation>> call, @NonNull Response<List<Conversation>> response) {
                Log.d("HTTP 200", "Get Conversation By user id -> success");
                conversationsMutableLiveData.setValue(response.body());
                Log.d("RepoConversation", "" + conversationsMutableLiveData.getValue().size());
            }

            @Override
            public void onFailure(@NonNull Call<List<Conversation>> call, @NonNull Throwable t) {
                Log.d("HTTP 404", " Conversation not found");
            }
        });

        return conversationsMutableLiveData;

    }
}
