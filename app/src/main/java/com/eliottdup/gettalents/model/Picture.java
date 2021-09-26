package com.eliottdup.gettalents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Picture implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("path")
    @Expose
    private String path;
}
