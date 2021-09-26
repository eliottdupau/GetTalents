package com.eliottdup.gettalents.utils;

public class AuthenticationUtils {

    public static boolean checkSignInForm(String email, String password) {
        return email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
                && password.length() > 7;
    }

    public static boolean checkSignUpForm(String email, String password, String confirmPassword){
        return email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
                && password.length() > 7
                && confirmPassword.equals(password) ;
    }

    public static boolean checkEmail(String email) {
        return email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
    }

    public static boolean checkPassword(String password) {
        return password.length() > 7;
    }

    public static boolean checkConfirmPassword(String password, String confirmPassword) {
        return confirmPassword.equals(password);
    }

}
