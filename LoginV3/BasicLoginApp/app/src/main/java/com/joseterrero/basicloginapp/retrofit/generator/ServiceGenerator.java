package com.joseterrero.basicloginapp.retrofit.generator;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final String BASE_URL = "http://10.0.2.2:3000/api";


    private static Retrofit retrofit = null;

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Autenticacion tipoActual = null;


    private static OkHttpClient.Builder httpClientBuilder =
            new OkHttpClient.Builder();




    public static <S> S createService(Class<S> serviceClass, String username, String password) {
        if (!(username.isEmpty() || password.isEmpty())) {
            String credentials = Credentials.basic(username, password);
            return createService(serviceClass, credentials, Autenticacion.BASIC);
        }
        return createService(serviceClass, null, Autenticacion.SIN_AUTENTICACION);
    }

    public static <S> S createService(Class<S> serviceClass, final String authtoken, final Autenticacion tipo) {

        if (retrofit == null) {

            httpClientBuilder.interceptors().clear();

            httpClientBuilder.addInterceptor(logging);

            // Interceptor que añade dos encabezados
            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("User-Agent", "LoginApp")
                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });


            builder.client(httpClientBuilder.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }

}
