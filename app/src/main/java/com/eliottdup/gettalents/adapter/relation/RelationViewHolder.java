package com.eliottdup.gettalents.adapter.relation;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.eliottdup.gettalents.R;
import com.eliottdup.gettalents.model.User;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Todo() : Rajouter la liste de compétences en bas de la vue
// Todo() : Rajouter un bouton "coeur" pour supprimer le user de la liste des favoris
// Todo() : Rajouter la note globale du user
// Todo() : Rajouter le clique renvoyant sur le profil utilisateur
public class RelationViewHolder extends RecyclerView.ViewHolder {
    private final ImageView profilePicture;
    private final TextView pseudoView, distanceView;

    public RelationViewHolder(@NonNull View itemView) {
        super(itemView);

        profilePicture = itemView.findViewById(R.id.imageView_profilePicture);
        pseudoView = itemView.findViewById(R.id.textView_pseudo);
        distanceView = itemView.findViewById(R.id.textView_distance);
    }

    public void bindData(User user, RequestManager glide) {
        glide.load(user.getProfilePicture().getPath())
                .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                .centerCrop()
                .into(profilePicture);

        pseudoView.setText(user.getPseudo());
        // Todo() : Calculer la distance entre la pos du user courant et l'adresse principal de l'autre user
        distanceView.setText(String.format("%s (40km)", user.getAddresses().get(0).getCity()));
    }
}
