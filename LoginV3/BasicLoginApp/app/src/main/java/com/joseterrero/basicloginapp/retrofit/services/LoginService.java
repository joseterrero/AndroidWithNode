package com.joseterrero.basicloginapp.retrofit.services;

import com.joseterrero.basicloginapp.model.Login;
import com.joseterrero.basicloginapp.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("login")
    Call<LoginResponse> doLogin(@Body Login login);
}
