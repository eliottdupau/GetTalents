package com.eliottdup.gettalents.data;

import android.util.Log;

import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.Picture;
import com.eliottdup.gettalents.model.Review;
import com.eliottdup.gettalents.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import androidx.lifecycle.MutableLiveData;

// Todo() : Rajouter les dernières méthodes CRUD
public class UserRepository {
    private final List<User> userList;

    public UserRepository() {
        userList = new ArrayList<>();

        initData();
    }

    // Todo() : Mettre à jour la méthode une fois le back créé
    public MutableLiveData<User> getUserById(String id) {
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

        for (User user : userList) {
            if (user.getId().equals(id)) {
                userMutableLiveData.setValue(user);
                Log.d("HTTP 200", "Get User -> success");
                break;
            }
        }

        return userMutableLiveData;
    }

    // Todo() : Mettre à jour la méthode une fois le back créé
    public MutableLiveData<User> getLoggedUser() {
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

        userMutableLiveData.setValue(userList.get(0));
        Log.d("HTTP 200", "Get Logged User -> success");

        return userMutableLiveData;
    }

    // Todo() : Mettre à jour la méthode une fois le back créé
    public MutableLiveData<User> updateUser(String id, User user) {
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId().equals(id)) {
                userList.set(i, user);
                userMutableLiveData.setValue(user);
                Log.d("HTTP 201", "Update User -> success");
                break;
            }
        }

        return userMutableLiveData;
    }

    User user = new User("1");
    User user1 = new User("2");
    User user2 = new User("3");

    // Todo() : Supprimer cette méthode et accéder au data via un appel API avec retrofit
    private void initData() {
        //User user = new User(UUID.randomUUID().toString());
        user.setPseudo("Lataupedu59");
        user1.setPseudo("Pierrelaf");
        user2.setPseudo("MickeyMousse");
        user.setMail("rene.lataupe@gmail.com");
        user1.setMail("pierre.lafond@gmail.com");
        user2.setMail("mickey.mousse@gmail.com");

        Picture picture = new Picture("0");
        Picture picture1 = new Picture("1");
        Picture picture2 = new Picture("2");
        picture.setUri("https://torange.biz/photofxnew/76/HD/lion-profile-picture-76801.jpg");
        picture1.setUri("https://geo.img.pmdstatic.net/fit/http.3A.2F.2Fprd2-bone-image.2Es3-website-eu-west-1.2Eamazonaws.2Ecom.2FGEO.2Fvar.2Fgeo.2Fstorage.2Fimages.2Fmedia.2Fimages.2Fgirafe.2F2410391-1-fre-FR.2Fgirafe.2Ejpg/768x441/background-color/ffffff/quality/70/cinq-choses-que-vous-ne-saviez-pas-sur-la-girafe.jpg");
        picture2.setUri("https://solution-nuisible.fr/wp-content/uploads/2020/05/souris-nuisible.jpg");
        user.setProfilePicture(picture);
        user1.setProfilePicture(picture1);
        user2.setProfilePicture(picture2);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 4);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.YEAR, 1996);
        user.setBirthday(calendar.getTime());
        user1.setBirthday(calendar.getTime());
        user2.setBirthday(calendar.getTime());

        addAddress(user);
        addAddress(user1);
        addAddress(user2);
        addRelation(user);
        addReview(user);
        addReview(user1);
        addReview(user2);

        userList.add(user);
        userList.add(user1);
        userList.add(user2);
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
        List<User> relationList = new ArrayList<>();

        relationList.add(user1);
        relationList.add(user2);

        user.setRelationList(relationList);
    }

    private void addReview(User user) {
        List<Review> reviewList = new ArrayList<>();

        Review review = new Review(UUID.randomUUID().toString());
        review.setComment("My fabulous comment, Lorem ipsum et colores templum pli plo plo plorum plis plis");
        review.setRating(1.5f);

        Review review2 = new Review(UUID.randomUUID().toString());
        review2.setComment("Another wonderful review from myself");
        review2.setRating(4.0f);

        reviewList.add(review);
        reviewList.add(review2);

        user.setReviewList(reviewList);
    }
}
