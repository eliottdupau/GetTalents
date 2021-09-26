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
public class Conversation implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("userPseudoList")
    @Expose
    private List<String> userPseudoList = null;
    @SerializedName("dateLastMessage")
    @Expose
    private String dateLastMessage;
    @SerializedName("lastContent")
    @Expose
    private String lastContent;

}
