package com.eliottdup.gettalents.model;

import java.io.Serializable;
import java.util.Date;
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

    private String id;
    private String pseudo;
    private String mail;
    private Date birthday;
    private Picture profilePicture;
    private List<Address> addresses;
    private List<User> relationList;
    private List<Review> reviewList;

    public User(String id) {
        this.id = id;
    }

    /*public boolean isInFavorite(String userId) {
        boolean inFavorite = false;

        if (!relationListId.isEmpty()) {
            for (String relationId : relationListId) {
                if (relationId.equals(userId)) {
                    inFavorite = true;
                    break;
                }
            }
        }

        return inFavorite;
    }*/
}
