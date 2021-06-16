package com.eliottdup.gettalents.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Photo {

    private String id;
    private String uri;

    public Photo(String id) {
        this.id = id;
    }
}
