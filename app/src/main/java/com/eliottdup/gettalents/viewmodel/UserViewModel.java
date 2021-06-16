package com.eliottdup.gettalents.viewmodel;

import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.Photo;
import com.eliottdup.gettalents.model.Review;
import com.eliottdup.gettalents.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
        //User user = new User("1");
        User user = new User(UUID.randomUUID().toString());
        user.setPseudo("Lataupedu59");
        user.setMail("rene.lataupe@gmail.com");
        user.setUrlProfilePicture("https://torange.biz/photofxnew/76/HD/lion-profile-picture-76801.jpg");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 4);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.YEAR, 1996);
        user.setBirthday(calendar.getTime());

        addAddress(user);

        addRelation(user);

        addReview(user);

        return user;
    }

    private void addAddress(User user) {
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
    }

    private void addRelation(User user) {
        List<String> relationsId = new ArrayList<>();
        String relationId1 = user.getId();
        String relationId2 = UUID.randomUUID().toString();

        relationsId.add(relationId1);
        relationsId.add(relationId2);

        user.setRelationListId(relationsId);
    }

    private void addReview(User user) {
        List<Review> reviewList = new ArrayList<>();

        Review review = new Review(UUID.randomUUID().toString());
        review.setComment("My fabulous comment");
        review.setRating(1.5f);

        Review review2 = new Review(UUID.randomUUID().toString());
        review2.setComment("Another wonderful review from myself");
        review2.setRating(4.0f);

        reviewList.add(review);
        reviewList.add(review2);

        user.setReviewList(reviewList);
    }
}
