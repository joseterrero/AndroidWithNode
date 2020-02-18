package com.joseterrero.basicloginapp.retrofit.generator;

import com.joseterrero.basicloginapp.common.Utilidades;
import com.joseterrero.basicloginapp.retrofit.services.LoginService;
import com.joseterrero.basicloginapp.retrofit.services.RegisterService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static ServiceGenerator serviceGenerator = null;
    private LoginService loginService;
    private RegisterService registerService;
    private static Retrofit retrofit;

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClientBuilder =
            new OkHttpClient.Builder().addInterceptor(logging);

    public ServiceGenerator(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Utilidades.BASE_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        loginService = retrofit.create(LoginService.class);
        registerService = retrofit.create(RegisterService.class);
    }

    public static ServiceGenerator getInstance(){
        if(serviceGenerator==null){
            serviceGenerator = new ServiceGenerator();
        }
        return serviceGenerator;
    }

    public LoginService getLoginService(){
        return loginService;
    }

    public RegisterService getRegisterService() {
        return registerService;
    }

}
