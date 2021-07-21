package com.eliottdup.gettalents.model;

import java.io.Serializable;
import java.util.ArrayList;
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

    private String id;
    private String userId;
    private float rating;
    private String comment;
    private List<Picture> pictureList;

    public Review(String id) {
        this.id = id;

        this.comment = "";
        this.pictureList = new ArrayList<>();
    }
}
