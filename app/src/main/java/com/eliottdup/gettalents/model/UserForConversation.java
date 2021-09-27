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
public class UserForConversation implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("firebaseUid")
    @Expose
    private String firebaseUid;

    @SerializedName("pseudo")
    @Expose
    private String pseudo;

}
