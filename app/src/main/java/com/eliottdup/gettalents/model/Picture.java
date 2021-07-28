package com.eliottdup.gettalents.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Picture {

    private String id;
    private String uri;

    public Picture(String id) {
        this.id = id;
    }
}
