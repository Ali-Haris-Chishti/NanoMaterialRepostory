package com.example.nmr.model;

public class CurrentUser {
    static User currentUser;
    private CurrentUser(){
    }

    static public void set(User user){
        currentUser = user;
    }
    static public User get(){
        if (currentUser == null)
            return new User();
        return currentUser;
    }
}
