package com.joseterrero.basicloginapp.retrofit.services;

import com.joseterrero.basicloginapp.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ListService {
    @GET("users")
    Call<List<User>> listUsers(@Header("Authorization") String authorization);
}
