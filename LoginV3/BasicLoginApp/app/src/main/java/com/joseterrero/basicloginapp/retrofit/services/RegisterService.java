package com.joseterrero.basicloginapp.retrofit.services;

import com.joseterrero.basicloginapp.model.RegisterResponse;
import com.joseterrero.basicloginapp.model.Registro;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("register")
    Call<RegisterResponse> doRegister(@Body Registro registro);
}
