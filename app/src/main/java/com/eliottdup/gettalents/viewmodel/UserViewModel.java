package com.eliottdup.gettalents.viewmodel;

import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> user;

    public void setUser(User user) {
        this.user.setValue(user);
    }
    public LiveData<User> getUser() {
        if (this.user == null) {
            this.user = new MutableLiveData<>();

            setUser(retrieveUser());
        }

        return this.user;
    }

    private User retrieveUser() {
        User user = new User(UUID.randomUUID().toString());
        user.setPseudo("Lataupedu59");
        user.setMail("rene.lataupe@gmail.com");
        user.setUrlProfilePicture("https://torange.biz/photofxnew/76/HD/lion-profile-picture-76801.jpg");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 4);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.YEAR, 1996);
        user.setBirthday(calendar.getTime());
        ArrayList<Address> addresses = new ArrayList<>();

        Address address = new Address(UUID.randomUUID().toString());
        address.setAddress("34 rue des peupliers");
        address.setZipCode("59360");
        address.setCity("Seclin");
        address.setCountry("France");

        Address address2 = new Address(UUID.randomUUID().toString());
        address2.setAddress("122 rue des grands arbres qui poussent vite");
        address2.setZipCode("60000");
        address2.setCity("Saint Quentin");
        address2.setCountry("France");

        Address address3 = new Address(UUID.randomUUID().toString());
        address3.setAddress("26 avenue des puits");
        address3.setZipCode("02387");
        address3.setCity("Compiègne");
        address3.setCountry("France");

        addresses.add(address);
        addresses.add(address2);
        addresses.add(address3);

        user.setAddresses(addresses);

        return user;
    }
}
