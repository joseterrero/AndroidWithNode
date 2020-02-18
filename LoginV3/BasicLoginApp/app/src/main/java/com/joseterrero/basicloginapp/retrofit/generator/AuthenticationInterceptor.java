package com.joseterrero.basicloginapp.retrofit.generator;

import com.joseterrero.basicloginapp.common.SharedPreferencesUI;
import com.joseterrero.basicloginapp.common.Utilidades;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import org.jetbrains.annotations.NotNull;

class AuthenticationInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        String token = SharedPreferencesUI.getStringValue(Utilidades.PREF_TOKEN);
        Request request = chain.request().newBuilder().addHeader("Authorization","Bearer " + token).build();
        return chain.proceed(request);
    }
}
