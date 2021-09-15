package com.eliottdup.gettalents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Address implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("number")
    @Expose
    private String numerous;

    @SerializedName("street")
    @Expose
    private String street;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("postalCode")
    @Expose
    private String postalCode;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("lng")
    @Expose
    private int lng;

    @SerializedName("lat")
    @Expose
    private int lat;

    @SerializedName("userId")
    @Expose
    private int userId;

    public Address() {
        this.numerous = "";
        this.street = "";
        this.postalCode = "";
        this.city = "";
    }
}
