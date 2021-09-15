package com.eliottdup.gettalents.data.service;

import com.eliottdup.gettalents.model.Address;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AddressService {

    @POST("addresses")
    Call<Address> createAddress(@Body Address address);

    @GET("addresses")
    Call<List<Address>> getAllAddresses();

    @GET("addresses/{id}")
    Call<Address> getAddressById(@Path("id") int id);

    @PATCH("addresses/{id}")
    Call<Void> updateAddress(@Path("id") int id,
                          @Body Address address);

    @DELETE("addresses/{id}")
    Call<Void> deleteAddress(@Path("id") int id);

    @GET("addresses/user/{userId}")
    Call<List<Address>> getAddressesForUser(@Path("userId") int userId);
}
