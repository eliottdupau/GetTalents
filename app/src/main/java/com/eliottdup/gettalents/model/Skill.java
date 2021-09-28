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
public class Skill implements Serializable {

    @SerializedName("idskill")
    @Expose
    private Integer idskill;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private Category category;
//    @SerializedName("users")
//    @Expose
//    private List<User> users = null;

}
