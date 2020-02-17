package com.joseterrero.basicloginapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("token")
    @Expose
    private String token;
    private String password;

    public LoginResponse(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
