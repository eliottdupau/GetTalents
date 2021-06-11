package com.eliottdup.gettalents.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    private String id;
    private String address;
    private String zipCode;
    private String city;
    private String country;

    public Address(String id) {
        this.id = id;

        this.address = "";
        this.zipCode = "";
        this.city = "";
        this.country = "";
    }
}
