package com.eliottdup.gettalents.viewmodel;

<<<<<<< HEAD
import com.eliottdup.gettalents.model.Address;
import com.eliottdup.gettalents.model.Picture;
=======
import android.app.Application;
import android.util.Log;

import com.eliottdup.gettalents.data.repository.ReviewRepository;
import com.eliottdup.gettalents.data.repository.UserRepository;
import com.eliottdup.gettalents.model.Review;
>>>>>>> d46e51dc00e396aa192c34c9542d929d1b2e4726
import com.eliottdup.gettalents.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    private MutableLiveData<User> user;
    public MutableLiveData<List<Review>> reviewList = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);

        userRepository = new UserRepository();
        reviewRepository = new ReviewRepository();
    }

    public void setUser(User user) {
        this.user.setValue(user);
    }

<<<<<<< HEAD
//            setUser(retrieveUser());
        }
=======
    public LiveData<User> getUser() {
        if (this.user == null) this.user = new MutableLiveData<>();
>>>>>>> d46e51dc00e396aa192c34c9542d929d1b2e4726

        return this.user;
    }
  
    public void getUserById(int id) {
        this.user = userRepository.getUserById(id);
    }

<<<<<<< HEAD
//    private User retrieveUser() {
//        User user = new User(UUID.randomUUID().toString());
//        user.setPseudo("Lataupedu59");
//        user.setMail("rene.lataupe@gmail.com");
//        Picture picture_temp = new Picture();
//        picture_temp.setId(4);
//        picture_temp.setPath("https://torange.biz/photofxnew/76/HD/lion-profile-picture-76801.jpg");
//        user.setProfilePicture(picture_temp);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, 4);
//        calendar.set(Calendar.MONTH, 0);
//        calendar.set(Calendar.YEAR, 1996);
//        user.setBirthday(calendar.getTime());
//
//        ArrayList<Address> addresses = new ArrayList<>();
//
//        Address address = new Address(UUID.randomUUID().toString());
//        address.setAddress("34 rue des peupliers");
//        address.setZipCode("59360");
//        address.setCity("Seclin");
//        address.setCountry("France");
//
//        Address address2 = new Address(UUID.randomUUID().toString());
//        address2.setAddress("122 rue des grands arbres qui poussent vite");
//        address2.setZipCode("60000");
//        address2.setCity("Saint Quentin");
//        address2.setCountry("France");
//
//        Address address3 = new Address(UUID.randomUUID().toString());
//        address3.setAddress("26 avenue des puits");
//        address3.setZipCode("02387");
//        address3.setCity("Compiègne");
//        address3.setCountry("France");
//
//        addresses.add(address);
//        addresses.add(address2);
//        addresses.add(address3);
//
//        user.setAddresses(addresses);
//
//        return user;
//    }
=======
    public void getLoggedUser() {
        this.user = userRepository.getUserById(1);
    }

    public void updateUser(int id, User user) {
        userRepository.updateUser(id, user);
    }

    public void getReceivedReviewsForUser(int userId) {
        reviewList = reviewRepository.getReceivedReviewsForUser(userId);
    }
>>>>>>> d46e51dc00e396aa192c34c9542d929d1b2e4726
}
