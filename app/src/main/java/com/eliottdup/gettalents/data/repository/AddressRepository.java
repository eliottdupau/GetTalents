package com.eliottdup.gettalents.data.repository;

import android.util.Log;

import com.eliottdup.gettalents.data.RetrofitInstance;
import com.eliottdup.gettalents.data.service.AddressService;
import com.eliottdup.gettalents.data.service.UserService;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressRepository {

    public AddressRepository() { }

    public MutableLiveData<Address> createAddress(Address address) {
        MutableLiveData<Address> addressMutableLiveData = new MutableLiveData<>();

        AddressService addressService = RetrofitInstance.getInstance().create(AddressService.class);
        Call<Address> call = addressService.createAddress(address);
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
                Log.d("HTTP 201", "Address Creation -> success");

                addressMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Address> call, @NonNull Throwable t) {
                Log.e("HTTP", "Address Creation -> fail");
            }
        });

        return addressMutableLiveData;
    }

    public MutableLiveData<Address> getAddressById(int id) {
        MutableLiveData<Address> addressMutableLiveData = new MutableLiveData<>();

        AddressService addressService = RetrofitInstance.getInstance().create(AddressService.class);
        Call<Address> call = addressService.getAddressById(id);
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
                Log.d("HTTP 200", "Get User by Id -> success");
                addressMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Address> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "User Not Found");
            }
        });

        return addressMutableLiveData;
    }

    public MutableLiveData<List<Address>> getAllAddresses() {
        MutableLiveData<List<Address>> addressesMutableLiveData = new MutableLiveData<>();

        AddressService addressService = RetrofitInstance.getInstance().create(AddressService.class);
        Call<List<Address>> call = addressService.getAllAddresses();
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(@NonNull Call<List<Address>> call, @NonNull Response<List<Address>> response) {
                Log.d("HTTP 200", "Get All Addresses -> success");
                addressesMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Address>> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "User Not Found");
            }
        });

        return addressesMutableLiveData;
    }

    public void updateAddress(int id, Address address) {
        AddressService addressService = RetrofitInstance.getInstance().create(AddressService.class);
        Call<Void> call = addressService.updateAddress(id, address);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("HTTP 200", "Get User by Id -> success");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "User Not Found");
            }
        });
    }

    public void deleteAddress(int id) {
        AddressService addressService = RetrofitInstance.getInstance().create(AddressService.class);
        Call<Void> call = addressService.deleteAddress(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("HTTP 200", "Delete Address -> success");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "User Not Found");
            }
        });
    }

    public MutableLiveData<List<Address>> getAddressesForUser(int userId) {
        MutableLiveData<List<Address>> addressesMutableLiveData = new MutableLiveData<>();

        AddressService addressService = RetrofitInstance.getInstance().create(AddressService.class);
        Call<List<Address>> call = addressService.getAddressesForUser(userId);
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(@NonNull Call<List<Address>> call, @NonNull Response<List<Address>> response) {
                Log.d("HTTP 200", "Get All Addresses -> success");
                addressesMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Address>> call, @NonNull Throwable t) {
                Log.e("HTTP 404", "User Not Found");
            }
        });

        return addressesMutableLiveData;
    }
}
