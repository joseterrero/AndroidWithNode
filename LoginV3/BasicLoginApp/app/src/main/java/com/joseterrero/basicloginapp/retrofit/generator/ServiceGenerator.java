package com.joseterrero.basicloginapp.retrofit.generator;

import android.content.Context;
import android.text.TextUtils;

import com.joseterrero.basicloginapp.common.Utilidades;
import com.joseterrero.basicloginapp.retrofit.services.LoginService;
import com.joseterrero.basicloginapp.retrofit.services.RegisterService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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



    /*-----------------------------------------------------------------------*/


    static Context ctx;

    public ServiceGenerator(Context ctx) {
        this.ctx = ctx;
    }

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS);

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Utilidades.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass, String username, String password){
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken){
        if(!TextUtils.isEmpty(authToken)){
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);

            if(!okHttpClient.interceptors().contains(interceptor)){
                httpClient.addInterceptor(interceptor);
                httpClient.addInterceptor(loggingInterceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass) {
        if(!okHttpClient.interceptors().contains(loggingInterceptor)){
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    String token = ctx.getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE).getString("PREF_TOKEN", null);
                    Request original = chain.request();


                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", "Bearer "+token);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            httpClient.addInterceptor(loggingInterceptor);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

}
