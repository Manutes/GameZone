package com.example.gamezone.data.models;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String imageUri;

    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public User(String username, String email, String imageUri) {
        this.username = username;
        this.email = email;
        this.imageUri = imageUri;
    }
}
