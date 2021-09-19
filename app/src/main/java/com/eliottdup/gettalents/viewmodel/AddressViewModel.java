package com.eliottdup.gettalents.viewmodel;

import android.app.Application;
import android.util.Log;

import com.eliottdup.gettalents.data.repository.AddressRepository;
import com.eliottdup.gettalents.data.repository.UserRepository;
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.User;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AddressViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public MutableLiveData<User> user;
    public MutableLiveData<Address> address;
    public MutableLiveData<List<Address>> addressList;

    public AddressViewModel(@NonNull Application application) {
        super(application);

        userRepository = new UserRepository();
        addressRepository = new AddressRepository();
    }

    public void addAddressInList(Address address) {
        List<Address> addressList = this.addressList.getValue();

        if (addressList != null) {
            addressList.add(address);
        }

        this.addressList.setValue(addressList);
    }

    public void updateAddressInList(Address address) {
        List<Address> addressList = this.addressList.getValue();

        if (addressList != null && addressList.size() > 0) {
            for (int i = 0; i < addressList.size(); i++) {
                if (addressList.get(i).getId() == address.getId()) {
                    addressList.set(i, address);
                    break;
                }
            }
        }

        this.addressList.setValue(addressList);
    }

    public void deleteAddressInList(Address address) {
        List<Address> addressList = this.addressList.getValue();

        if (addressList != null && addressList.size() > 0) {
            for (int i = 0; i < addressList.size(); i++) {
                if (addressList.get(i).getId() == address.getId()) {
                    addressList.remove(i);
                    break;
                }
            }
        }

        this.addressList.setValue(addressList);
    }

    public void getLoggedUser(String id) {
        // Todo() : Changer avec la méthode de réccupération du user logged
        this.user = userRepository.getUserById(id);
    }

    public void updateAddress(int addressId, Address address) {
        addressRepository.updateAddress(addressId, address);
    }

    public LiveData<Address> createAddress(Address address) {
        return addressRepository.createAddress(address);
    }

    public void deleteAddress(int addressId) {
        addressRepository.deleteAddress(addressId);
    }

    public void getAllAddressesForUser(int userId) {
        this.addressList = addressRepository.getAddressesForUser(userId);
    }

    public MutableLiveData<List<Address>> getAddressList() {
        if (addressList == null) addressList = new MutableLiveData<>();

        return addressList;
    }
}
