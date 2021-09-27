package com.eliottdup.gettalents.adapter.Conversation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.Conversation;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.viewmodel.ChatViewModel;

import java.util.List;

public class ConversationAdapteur extends RecyclerView.Adapter<ConversationViewHolder> {

    private List<Conversation> conversations;
    private  Context context;

    public ConversationAdapteur(List<Conversation> conversations,Context context){
        this.conversations = conversations;
        this.context = context;
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_conversation, parent, false);

        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        holder.bind(this.conversations.get(position),context);
    }

    @Override
    public int getItemCount() {
        return this.conversations.size();
    }

    public void updateData(List<Conversation> conversations) {
        this.conversations = conversations;
        notifyDataSetChanged();
    }

    public Conversation getConversation(int position){
        return  this.conversations.get(position);
    }
}
