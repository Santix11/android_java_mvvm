package com.example.haystax.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class loginResponse {
    //tells retrofit what to look for in a response
    @SerializedName("username")
    //gson convertor can deserialize and serialize the data from the web response
    @Expose()
    private String username;

    private String email;
    private String password;

    public loginResponse(String email, String password) {
        this.email = email;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "loginResponse{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
