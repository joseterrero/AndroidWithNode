package com.joseterrero.basicloginapp.retrofit.services;

import com.joseterrero.basicloginapp.model.LoginResponse;
import com.joseterrero.basicloginapp.model.RegisterResponse;
import com.joseterrero.basicloginapp.model.Registro;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RegisterService {
    @POST("register")
    Call<RegisterResponse> doRegister(@Body Registro registro);

    @Multipart
    @POST("register")
    Call<RegisterResponse> doRegister(@Part("email") RequestBody email,
                                   @Part("username") RequestBody username,
                                   @Part("password") RequestBody password,
                                   @Part("password2") RequestBody password2,
                                   @Part MultipartBody.Part avatar);
}
