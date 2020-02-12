package com.joseterrero.basicloginapp.retrofit.services;

import com.joseterrero.basicloginapp.model.User;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("/register")
    Call<User> doRegister(@Header("Authorization") String authorization);
}
