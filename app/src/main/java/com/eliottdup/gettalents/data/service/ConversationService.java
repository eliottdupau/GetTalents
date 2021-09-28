package com.eliottdup.gettalents.data.service;

import com.eliottdup.gettalents.model.Conversation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ConversationService {

    @GET("/conversations/GetConversationByUserId/{userId}")
    Call<List<Conversation>> GetConversationByUserId(@Path("userId") int userId);
}
