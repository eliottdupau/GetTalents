package com.eliottdup.gettalents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("pseudo")
    @Expose
    private String pseudo;
    @SerializedName("registrationDate")
    @Expose
    private Date registrationDate;
    @SerializedName("email")
    @Expose
    private String mail;
    @SerializedName("phone")
    @Expose
    private Integer phone;
    @SerializedName("presentation")
    @Expose
    private String presentation;
    @SerializedName("birthday")
    @Expose
    private Date birthday;
    @SerializedName("profilePicture")
    @Expose
    private Picture profilePicture;
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses;
    @SerializedName("skills")
    @Expose
    private List<Skill> skills;

    public User(String id) {
        this.id = id;
    }
}
