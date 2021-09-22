package com.eliottdup.gettalents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("pseudo")
    @Expose
    private String pseudo;

    @SerializedName("registrationDate")
    @Expose
    private String registrationDate;

    @SerializedName("available")
    @Expose
    private int available;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private int phone;

    @SerializedName("presentation")
    @Expose
    private String presentation;

    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("profilePicture")
    @Expose
    private Picture profilePicture;

    @SerializedName("addresses")
    @Expose
    private List<Address> addresses = null;

    @SerializedName("skills")
    @Expose
    private List<Skill> skills = null;

    /*private List<User> relationList;
    private List<Review> reviewList;*/

    // Todo() : Vérifier la fonction des favoris
    /*public boolean isInFavorite(String userId) {
        boolean inFavorite = false;

        if (!relationList.isEmpty()) {
            for (User user : relationList) {
                if (user.getId().equals(userId)) {
                    inFavorite = true;
                    break;
                }
            }
        }

        return inFavorite;
    }*/
}
