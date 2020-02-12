package com.joseterrero.basicloginapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("password")
    @Expose
    private String password;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
