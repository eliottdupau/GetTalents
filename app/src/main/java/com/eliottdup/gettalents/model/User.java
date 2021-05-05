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
    private String name;
    private String firstName;
    private String mail;
    private Date birthday;
    private String urlProfilePicture;
    private List<Address> addresses;

    public User(String id) {
        this.id = id;
    }
}