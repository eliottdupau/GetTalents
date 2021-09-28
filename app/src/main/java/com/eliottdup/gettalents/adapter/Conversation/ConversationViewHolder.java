package com.eliottdup.gettalents.adapter.Conversation;



import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.data.helper.FirebaseStorageHelper;
import com.eliottdup.gettalents.model.Conversation;
import com.eliottdup.gettalents.model.User;
import com.eliottdup.gettalents.model.UserForConversation;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class ConversationViewHolder extends RecyclerView.ViewHolder {

    public ImageView profilePicture;
    public TextView userName;
    public TextView lastMessage;

    public ConversationViewHolder(@NonNull View itemView) {
        super(itemView);
        userName = itemView.findViewById(R.id.userName);
        lastMessage = itemView.findViewById(R.id.lastMessage);
        profilePicture = itemView.findViewById(R.id.userProfilPicture);
    }

    public void bind(Conversation conversation, Context context) {

        UserForConversation user = new UserForConversation();

        for (UserForConversation user1: conversation.getUsers())
        {
            if (!user1.getFirebaseUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                user = user1 ;
            }
        }

        lastMessage.setText(conversation.getLastContent());
        userName.setText(user.getPseudo());

        FirebaseStorageHelper.downloadProfilePicture(
               context,
                profilePicture,
                "images/profile/" + user.getFirebaseUid()+".png");
    }



}
