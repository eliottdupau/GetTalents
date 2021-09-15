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
public class Review implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("note")
    @Expose
    private int note;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("updatedAt")
    @Expose
    private Object updatedAt;

    @SerializedName("pictures")
    @Expose
    private List<Picture> pictureList = null;

    @SerializedName("senderId")
    @Expose
    private int senderId;

    @SerializedName("sender")
    @Expose
    private User sender;

    @SerializedName("recipientId")
    @Expose
    private int recipientId;

    @SerializedName("recipient")
    @Expose
    private User recipient;
}
