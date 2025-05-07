package nje.hu.quickshop.utils;

public class Validator {

    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}
