package com.example.haystax.requests;

import com.google.gson.annotations.SerializedName;

public class Post {


    private String email;
    private String password;

    @SerializedName("body")
    private String text;

    public Post(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Post() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Post{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
