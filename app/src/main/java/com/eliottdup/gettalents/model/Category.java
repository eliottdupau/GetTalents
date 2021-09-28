package com.eliottdup.gettalents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by temp on 17/06/2021
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("categoryPicture")
    @Expose
    private Picture categoryPicture;
    @SerializedName("skills")
    @Expose
    private List<Skill> skills = null;
//    @SerializedName("users")
//    @Expose
//    private List<User> users = null;

//    public Category(String id) {
//        this.id = id;
//        this.name = "";
//        this.icon = "";
//    }

}
