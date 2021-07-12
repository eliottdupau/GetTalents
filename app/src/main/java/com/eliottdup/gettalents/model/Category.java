package com.eliottdup.gettalents.model;

import java.io.Serializable;

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
    private String id;
    private String name;
    private String icon;

    public Category(String id) {
        this.id = id;
        this.name = "";
        this.icon = "";
    }

}
